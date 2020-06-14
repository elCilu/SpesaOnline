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

import static utils.StringUtil.isValidEmail;

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
            Parent root = FXMLLoader.load(getClass().getResource("/views/signup.fxml"));
            stage.setScene(new Scene(root, 400, 350));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void logIn() {
        boolean logged = false;
        String email = emailField.getText();
        String password = passwordField.getText();
        try {
            if (email.trim().isEmpty() || !isValidEmail(email)) {
                actionTarget.setText("Campo email non valido!");
                throw new Exception("Campo email non valido.");
            }
            if (password.isEmpty()) {
                actionTarget.setText("Inserisci password");
                throw new Exception("Campo password vuoto.");
            }
            CredentialModel credentials = CredentialDao.selectByEmail(email);
            if (credentials != null) {
                if (CredentialUtil.checkPassword(password, credentials.getSalt(), credentials.getHash())) {
                    logged = true;
                    actionTarget.setText("Sei loggato.");
                }
            } else {
                actionTarget.setText("Registrati");
                throw new Exception("User not signed");
            }
            if(!logged) {
                actionTarget.setText("Credenziali sbagliate.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
