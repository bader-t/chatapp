package ma.ensa.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public static Stage stage;
    @FXML
    private AnchorPane ap_main;
    @FXML
    private Label lb_username;



    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_send;

    @FXML
    private ScrollPane sp_conversation;

    @FXML
    private ScrollPane sp_users;

    @FXML
    private TextField tf_message;

    @FXML
    private VBox vb_conversation;

    @FXML
    private VBox vb_users;

    public static Client client;

    public static String sendTo = "@toAll";


    public static String receiver;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            client = new Client(new Socket("localhost",1234));
            //send username to server to identify client flag="@loggedIn"
            client.setUsername(LogginController.username);
            client.sendMessage("@loggedIn", client.getUsername());
        }catch (IOException e){
            e.printStackTrace();
        }

        lb_username.setText(client.getUsername());

        vb_conversation.heightProperty().addListener((observableValue, number, t1) -> sp_conversation.setVvalue((Double) t1));
        vb_users.heightProperty().addListener((observableValue, number, t1) -> sp_users.setVvalue((Double) t1));

        client.receiveMessage(vb_conversation,vb_users);


        btn_send.setOnAction(actionEvent -> {
            String message = tf_message.getText();
            if (!message.isEmpty()){
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                Text text = new Text(message);
                text.setStyle("-fx-fill: white;");
                hBox.setPadding(new Insets(3,0,3,0));
                TextFlow textFlow = new TextFlow(text);
                textFlow.setPadding(new Insets(7,14,7,14));
                textFlow.setStyle("-fx-background-color:  #0088cc;-fx-background-radius: 20px;");
                hBox.getChildren().add(textFlow);
                vb_conversation.getChildren().add(hBox);
                client.sendMessage(sendTo+":"+client.getUsername(),message);
                tf_message.clear();
            }
        });
        btn_logout.setOnAction(actionEvent -> {
            try {

                Stage stage = (Stage) btn_logout.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loggin.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 700, 500);
                stage.setTitle("loggin");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }catch(IOException e){
                e.printStackTrace();
            }
            client.shutDown();

        });
       stage.setOnCloseRequest(event -> {
           client.sendMessage("@loggedOut", client.getUsername());
           System.exit(0);
       });

    }


    public static void addReceivedMessage(String message, VBox vb_conversation) {
        String [] data = message.split(":",2);
        HBox hBox0 = new HBox();
        hBox0.setAlignment(Pos.CENTER_LEFT);
        Text sender = new Text(data[0]);
        sender.setStyle("-fx-fill: gray;");
        hBox0.getChildren().add(sender);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        Text text = new Text(data[1]);
        text.setStyle("-fx-fill: black;");
        hBox.setPadding(new Insets(3,0,3,0));
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(7,14,7,14));
        textFlow.setStyle("-fx-background-color:  rgb(233,233,235);-fx-background-radius: 20px;");
        hBox.getChildren().add(textFlow);
        Platform.runLater(() -> vb_conversation.getChildren().addAll(hBox0,hBox));
    }

    public static void addConnectedUsers(String connectedUsers, VBox vb_users) {
        Platform.runLater(() -> vb_users.getChildren().clear());
        String users[] = connectedUsers.split(":");
        for(String user: users){
            if (!user.equals(client.getUsername())) {
                Button btn_user = new Button(user);
                btn_user.setPadding(new Insets(10,7,20,20));
                btn_user.setStyle("-fx-background-color:#0088cc;-fx-text-fill: white;-fx-font-size:16px;-fx-font-family:Calibri");
                btn_user.setAlignment(Pos.TOP_LEFT);
                btn_user.setMaxWidth(Double.MAX_VALUE);
                btn_user.setCursor(Cursor.HAND);
                btn_user.setOnAction(e->{
                    Button selected = (Button) e.getTarget();
                });
                Platform.runLater(() -> {
                    vb_users.getChildren().add(btn_user);
                });
            }
        }
    }

}
