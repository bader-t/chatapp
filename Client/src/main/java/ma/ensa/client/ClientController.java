package ma.ensa.client;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    private Client server;

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

    private Client client;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            client = new Client(new Socket("localhost",1234));
        }catch (IOException e){
        e.printStackTrace();
        }

        lb_username.setText(LogginController.username);

        vb_conversation.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sp_conversation.setVvalue((Double) t1);
            }
        });

        client.receiveMessage(vb_conversation);

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
                textFlow.setStyle("-fx-background-color:  #42A7C3;-fx-background-radius: 20px;");
                hBox.getChildren().add(textFlow);
                vb_conversation.getChildren().add(hBox);
                client.sendMessage(message);
                tf_message.clear();

            }
        });


    }


    public static void addReceivedMessage(String message, VBox vb_conversation) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        Text text = new Text(message);
        text.setStyle("-fx-fill: black;");
        hBox.setPadding(new Insets(3,0,3,0));
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(7,14,7,14));
        textFlow.setStyle("-fx-background-color:  rgb(233,233,235);-fx-background-radius: 20px;");
        hBox.getChildren().add(textFlow);
        Platform.runLater(() -> vb_conversation.getChildren().add(hBox));
    }

}