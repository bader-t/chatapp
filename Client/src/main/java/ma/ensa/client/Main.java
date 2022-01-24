package ma.ensa.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loggin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        Image logo = new Image(Main.class.getResource("icons/logo.png").toString());
        stage.getIcons().add(logo);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}