package controllers;

import dao.ClientDao;
import dao.PasswordDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.PasswordModel;
import utils.PasswordUtil;

public class LoginController {

    @FXML
    private GridPane loginPage;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Text actionTarget;

    @FXML
    protected void goToSignUp() {
        try {
            Stage stage = (Stage) loginPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/signup.fxml"));
            stage.setScene(new Scene(root, 400, 300));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void logIn() {
        //TODO: add controls (not null, isEmpty...)
        //TODO: 1 query w/ join statement instead of execute 2 queries
        boolean logged = false;
        int clientIdPassword = ClientDao.selectUserIdPasswordByUsername(emailField.getText());
        if (clientIdPassword == 0) {
            System.out.println("No client found");
        } else {
            PasswordModel password = PasswordDao.selectById(clientIdPassword);
            if (password == null) {
                System.out.println("No password found");
            } else {
                if (PasswordUtil.checkPassword(passwordField.getText(), password.getSalt(), password.getHash()))
                    logged = true;
            }
        }
        if (logged) {
            actionTarget.setText("Logged");
        } else {
            actionTarget.setText("No logged");
        }
    }

}
