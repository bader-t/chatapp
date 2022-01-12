package ma.ensa.client;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {

    private int ClientId;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    Client(Socket socket) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e){
            System.out.println("Error creating client!");
            e.printStackTrace();
            shutDown();
        }

    }


    public void receiveMessage(VBox vb_conversation) {
        new Thread(() -> {
            while (socket.isConnected()){
                try{
                    String message = bufferedReader.readLine();
                    ClientController.addReceivedMessage(message,vb_conversation);

                } catch(IOException e){
                    System.out.println("Error receiving message from !!!");
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        try{
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            System.out.println("Error sending message to !!!");
            e.printStackTrace();
        }
    }

    private void shutDown() {
        try{
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
            if (socket != null)
                socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
