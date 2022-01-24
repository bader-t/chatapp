package ma.ensa.client;

import javafx.event.ActionEvent;
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

public class LogginController implements Initializable {
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_signup;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_email;

    public static String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_login.setOnAction(ActionEvent->{
            tf_email.requestFocus();
            String password = tf_password.getText();
            DAO dao = new DAOImpl();
            String email = tf_email.getText();
            String hashedPass = "";
            if (!email.isEmpty() && !password.isEmpty()){
                try{
                    hashedPass= HashPassword.getHash(password);
                    System.out.println("this is the password typed by the user when the login get done " + hashedPass);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(!dao.login(email ,  hashedPass)){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Vertify Your Email Or Your Password");
                    alert.show();

                }
                else {
                    System.out.println("y u re in");
                    try {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        Thread.sleep(1000);
                        changeWindow("client.fxml", username, 700, 500);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Fill All the fields");
                alert.show();
            }
        });
        btn_signup.setOnAction(ActionEvent->{
            changeWindow("signup.fxml" ,"Sign Up" , 700 , 500);
        });

    }



    private void changeWindow(String fxml , String title , int size_w , int size_h)  {
       try {
            Stage stage = (Stage) tf_email.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), size_w, size_h);
           Image logo = new Image(Main.class.getResource("icons/logo.png").toString());
           stage.getIcons().add(logo);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e){
           e.printStackTrace();
       }
    }



}
