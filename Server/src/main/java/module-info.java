module ma.ensa.server {
    requires javafx.controls;
    requires javafx.fxml;


    opens ma.ensa.server to javafx.fxml;
    exports ma.ensa.server;
}