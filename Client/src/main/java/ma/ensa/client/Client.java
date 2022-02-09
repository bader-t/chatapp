package ma.ensa.client;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {


    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

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


    public void receiveMessage(VBox vb_conversation,VBox vb_users) {
        new Thread(() -> {
            while (socket.isConnected()){
                try{
                    String message = bufferedReader.readLine();
                    /*
                     * Types of comming messages from server
                     * message = "@toAllFrom:jhon:hello everyone" -> flag="@toAllFrom" sender="jhon" (message sent by sender="jhon" to all connected users)
                     * message = "@connectedUsers:abdelkarim:bader:faya:imad:mehdi:yessine" -> flag="@connectedUsers" (connected users separated with ":")
                     * default message = "faya:I'm fine thanks :)" ->  sender="faya" (message sent by user="faya")
                     * */

                    // split message to two parts :
                    String data[] = message.split(":",2);
                    // data[0] contains the flag or username, data[1] contains info or message

                    switch (data[0]){
                        case "@connectedUsers":
                            ClientController.addConnectedUsers(data[1],vb_users);
                            break;
                        case "@toAllFrom":
                            ClientController.addReceivedMessage(data[1],vb_conversation);
                            break;
                        default:
                            ClientController.addReceivedMessage(message,vb_conversation);
                            break;
                    }


                } catch(IOException e){
                    System.out.println("Error receiving message from !!!");
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }

    // head can be either a flag or username of sender
    public void sendMessage(String head,String message) {
        try{
            bufferedWriter.write(head+":"+message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            System.out.println("Error sending message to "+head);
            e.printStackTrace();
        }
    }

    public void shutDown() {
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
