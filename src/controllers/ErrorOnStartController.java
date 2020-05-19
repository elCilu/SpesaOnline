package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class ErrorOnStartController {
    @FXML
    private GridPane errorOnStart;

    public void closeApplication() {
        Stage stage = (Stage) errorOnStart.getScene().getWindow();
        stage.close();
    }
}
