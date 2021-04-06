module poker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.json;

    exports poker.client to javafx.graphics;
    opens poker.client.controller to javafx.fxml;
}