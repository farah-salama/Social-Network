module com.example.social_network {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens com.example.social_network to javafx.fxml;
    exports com.example.social_network;
}