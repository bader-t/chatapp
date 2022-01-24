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
    public String username;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }



    @Override
    public void run() {
        String message;
        while(socket.isConnected()){
            try {
                message = bufferedReader.readLine();
                /*
                * Types of comming messages from client
                * message = "@toAll:hello, how are you" -> flag="@toAll" (message to be sent to all connected users)
                * message = "@loggedIn:bader" -> flag="@loggedIn" (user="bader" logged in)
                * message = "@loggedOut:bader" -> flag="@loggedOut" (user="bader" logged out)
                * default message = "fred:I'm fine thanks :)" ->  sendToUser="fred" (message to be sent only to user="fred")
                * */

                // split message to two parts :
                String data[] = message.split(":",2);
                // data[0] contains the flag or username, data[1] contains info or message

                switch (data[0]){
                    case "@loggedIn":
                        username = data[1];
                        clientHandlers.add(this);
//                        broadcastConnectedUsers(clientHandlers.parseToString);
                        break;
                    case "@toAll":
                        broadcastMessage(message);
                        break;
                    case "@loggedOut":
                        userLoggedOut(data[1]);
                        break;
                    default:
                        broadcastMessageTo(data[0],data[1]);
                        break;
                }
            } catch (IOException e) {
                closeClientHandler(socket,bufferedReader,bufferedWriter);
                break;
            }
        }

    }

    private void broadcastConnectedUsers(String s) {
    }

    // send message to username
    private void broadcastMessageTo(String username, String message) {
    }


    // delete loggedOut user from connected users
    private void userLoggedOut(String username) {
    }

    private void broadcastMessage(String message) {
        for(ClientHandler clientHandler : clientHandlers){
            try {
                if (!clientHandler.username.equals(username)) {
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
