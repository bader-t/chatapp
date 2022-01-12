package ma.ensa.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
    public static List<ClientHandler> clientHandlers = new ArrayList<>();
    public Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        clientHandlers.add(this);
    }

    @Override
    public void run() {
        String message;
        while(socket.isConnected()){
            try {
                message = bufferedReader.readLine();
                broadcastMessage(message);
            } catch (IOException e) {
                closeClientHandler(socket,bufferedReader,bufferedWriter);
                break;
            }
        }

    }

    private void broadcastMessage(String message) {
        for(ClientHandler clientHandler : clientHandlers){
            try {
                if (!clientHandler.socket.getRemoteSocketAddress().equals(socket.getRemoteSocketAddress())) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }


            } catch (IOException e){
                System.out.println("Error sending message");
                e.printStackTrace();
            }
        }
    }

    public void removeHandler(){
        clientHandlers.remove(this);
    }

    public void closeClientHandler(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)  {
        try{
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
            if (socket != null)
                socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
