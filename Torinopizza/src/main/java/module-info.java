module com.example.torinopizza {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.torinopizza to javafx.fxml;
    exports com.example.torinopizza;
}