package ma.ensa.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ma.ensa.client.Utils.DAO.DAO;
import ma.ensa.client.Utils.DAO.DAOImpl;
import ma.ensa.client.Utils.HashPassword;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML
    private Button btn_loggin;

    @FXML
    private Button btn_signup;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        btn_signup.setOnAction(ActionEvent->{
            DAO dao = new DAOImpl();
            String username = tf_username.getText();
            String email = tf_email.getText();
            String password = tf_password.getText();
            String hashedPass = "" ;
            try {
                hashedPass = HashPassword.getHash(password);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if (!username.isEmpty() &&  !email.isEmpty() && !password.isEmpty()){
                System.out.println("this is the hashed password " + hashedPass);
                if(dao.add(username , email, hashedPass) == 0 ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("There was an error in your registration");
                    alert.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Saved Successfully");
                    alert.show();
                }
                //changeWindow("loggin.fxml",null , 400 , 400);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Fill All the fields");
                alert.show();
            }
        });
        btn_loggin.setOnAction(ActionEvent->{
            changeWindow("loggin.fxml" ,"Log In" , 700 , 500);
        });

    }



    private void changeWindow(String fxml , String title , int size_w , int size_h)  {
        try {
            Stage stage = (Stage) tf_username.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), size_w, size_h);
            Image image = new Image(Main.class.getResource("icons/logo.png").toString());
            stage.getIcons().add(image);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
