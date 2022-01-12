package ma.ensa.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogginController implements Initializable {
    @FXML
    private Button btn_loggin;
    @FXML
    private Button btn_signup;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_username;

    public static String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_loggin.setOnAction(ActionEvent->{
            username = tf_username.getText();
            if (!username.isEmpty()){

                    changeWindow(username);
            }
        });
    }

    private void changeWindow(String username)  {
       try {
            Stage stage = (Stage) tf_username.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("client.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle(username + "");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e){
           e.printStackTrace();
       }
    }
}
