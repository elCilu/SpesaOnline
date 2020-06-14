module SpesaOnline {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires javafx.graphics;

    opens controllers to javafx.fxml;
    opens sample;
}