package controllers;

import dao.CredentialDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.CredentialModel;
import utils.CredentialUtil;

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
            stage.setScene(new Scene(root, 400, 350));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void logIn() {
        boolean validEmail = false;
        boolean validPassword = false;
        String email = emailField.getText();
        String password = passwordField.getText();
        try {
            if (password.isEmpty()) {
                actionTarget.setText("Inserisci password");
                System.err.println("Campo password vuoto.");
            } else {
                validPassword = true;
            }
            if (email.trim().isEmpty() || !email.matches("[a-zA-Z.0-9]+@[a-zA-Z]+\\.[a-z]{2,3}$")) {
                actionTarget.setText("Campo email non valido!");
                System.err.println("Campo email non valido.");
            } else {
                validEmail = true;
            }
            if (validEmail && validPassword) {
                CredentialModel credentials = CredentialDao.selectByEmail(email);
                if (credentials != null) {
                    if (CredentialUtil.checkPassword(password, credentials.getSalt(), credentials.getHash())) {
                        actionTarget.setText("Sei loggato.");
                    } else {
                        actionTarget.setText("Credenziali errate.");
                    }
                } else {
                    actionTarget.setText("Utente non registrato.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
