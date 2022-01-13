package ma.ensa.client.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
    private Connection conn ;

    private Statement st ;
    public ConnectDB(){
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/user" ,"root" ,"" );
            st = conn.createStatement();
        }
        catch ( SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return this.conn;
    }

    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
