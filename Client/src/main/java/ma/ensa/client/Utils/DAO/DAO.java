package ma.ensa.client.Utils.DAO;

public interface DAO {
    int add(String name , String email , String password);
    boolean login(String email , String password );
}
