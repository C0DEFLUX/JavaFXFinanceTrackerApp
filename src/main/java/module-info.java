module com.example.javafxfinancetrackerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javafxfinancetrackerapp to javafx.fxml;
    opens model to javafx.base;
    exports com.example.javafxfinancetrackerapp;
}