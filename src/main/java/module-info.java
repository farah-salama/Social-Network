module com.example.social_network {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.social_network to javafx.fxml;
    exports com.example.social_network;
}