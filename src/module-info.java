module SpesaOnline {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires javafx.graphics;
    requires com.jfoenix;

    opens controllers to javafx.fxml;
    opens sample;
}