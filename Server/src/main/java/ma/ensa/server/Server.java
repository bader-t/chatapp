package ma.ensa.server;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {


    private ServerSocket serverSocket;
    Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("server launched");
    }

    @Override
    public void run() {
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                System.out.println(clientHandler.username +" "+ socket.getRemoteSocketAddress() +" is connected");
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e ) {
            e.printStackTrace();
        }
    }



    public void closeServerSocket()  {
        try{
            if (serverSocket != null)
                serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
