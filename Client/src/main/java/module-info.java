module ma.ensa.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens ma.ensa.client to javafx.fxml;
    exports ma.ensa.client;
}