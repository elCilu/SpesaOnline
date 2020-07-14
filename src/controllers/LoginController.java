package controllers;

import dao.ClientDao;
import dao.CredentialDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.CredentialModel;
import sample.GlobalVars;
import utils.CredentialUtil;

import static utils.StringUtil.isValidEmail;

public class LoginController {

    @FXML
    private AnchorPane loginPage;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    protected void goToSignUp() {
        try {
            Stage stage = (Stage) loginPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/signup.fxml"));
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void login() {
        boolean logged = false;
        String email = emailField.getText();
        String password = passwordField.getText();
        try {
            if (email.trim().isEmpty() || !isValidEmail(email)) {
                throw new Exception("Campo email non valido.");
            }
            if (password.isEmpty()) {
                throw new Exception("Campo password vuoto.");
            }
            CredentialModel credentials = CredentialDao.selectByCredential(email);
            if (credentials != null) {
                if (CredentialUtil.checkPassword(password, credentials.getSalt(), credentials.getHash())) {
                    logged = true;
                    GlobalVars.USER_ID = ClientDao.selectIdByEmail(email);
                    goToShopping();
                }
            } else {
                throw new Exception("User not signed");
            }
            if (!logged) {
                //Da aggiungere qualcosa
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void goToShopping() {
        try {
            Stage stage = (Stage) loginPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/shopping.fxml"));
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToAdminLogin() {
        try {
            Stage stage = (Stage) loginPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/cart.fxml"));
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
