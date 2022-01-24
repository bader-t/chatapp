package ma.ensa.client.Utils.DAO;
import ma.ensa.client.LogginController;
import ma.ensa.client.Utils.ConnectDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOImpl implements DAO {
    private ConnectDB db;
    public DAOImpl(){
        db = new ConnectDB();
    }
    @Override
    public int add(String username ,String email , String password) {
        String req = "INSERT INTO users values(?,?,?);";
        int r = 0;
        try{
            db = new ConnectDB();
            PreparedStatement prst = db.getConn().prepareStatement(req);
            prst.setString(1,username);
            prst.setString(2,email);
            prst.setString(3,password);
            r= prst.executeUpdate();
            System.out.println("the user with the username " + username + " is saved successfully" );

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("there was an error in the sign in process please verify your coordinates");
        }
        return r;
    }

    @Override
    public boolean login(String email, String password) {
        String req = "SELECT * FROM users WHERE email= ? and password = ?";
        try {
            db  = new ConnectDB();
            PreparedStatement preparedStatement = db.getConn().prepareStatement(req);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LogginController.username=resultSet.getString("username");
                return true;
            }
        }
        catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }
        return false ;
    }

}
