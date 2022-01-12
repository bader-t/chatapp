package ma.ensa.server;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    public Server server;

    @FXML
    private AnchorPane ap_main;

    @FXML
    private ScrollPane sp_main;

    @FXML
    private Button btn_lanch;

    @FXML
    private Button btn_off;



    @FXML
    private TextField tf_port;

    @FXML
    private VBox vb_users;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_lanch.setOnAction(actionEvent -> {
            int port = Integer.parseInt(tf_port.getText());
            try {
                server = new Server(port);
                server.start();
                btn_off.setOpacity(1);
                tf_port.setDisable(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_off.setOnAction(actionEvent -> {
            server.closeServerSocket();
            tf_port.setDisable(false);
            btn_off.setOpacity(0);
        });

        vb_users.heightProperty().addListener((observableValue, number, t1) -> sp_main.setVvalue((Double) t1));





    }




}