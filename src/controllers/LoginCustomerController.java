package controllers;

import dao.ClientDao;
import dao.CredentialDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.CredentialModel;
import sample.Global;
import utils.CredentialUtil;

import static utils.StringUtil.isValidEmail;

public class LoginCustomerController {

    @FXML
    private AnchorPane loginPage;
    @FXML
    private Label actionLabel;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    protected void goToSignUp() {
        Global.changeScene(loginPage, "signup");
    }

    @FXML
    protected void login() {
        boolean logged = false;
        String email = emailField.getText();
        String password = passwordField.getText();
        try {
            if (email.trim().isEmpty() || !isValidEmail(email)) {
                actionLabel.setText("Campo email non valido");
                actionLabel.setVisible(true);
                return;
            }
            if (password.isEmpty()) {
                actionLabel.setText("Campo password vuoto");
                actionLabel.setVisible(true);
                return;
            }
            CredentialModel credentials = CredentialDao.selectByCredential(email);
            if (credentials != null) {
                if (CredentialUtil.checkPassword(password, credentials.getSalt(), credentials.getHash())) {
                    logged = true;
                    Global.USER_ID = ClientDao.selectIdByEmail(email);
                    goToShopping();
                }
            } else {
                actionLabel.setText("Utente non registrato");
                actionLabel.setVisible(true);
                return;
            }
            if (!logged) {
                actionLabel.setText("Credenziali errate");
                actionLabel.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void goToShopping() {
        Global.changeScene(loginPage, "shopping");
    }

    public void goToAdminLogin() {
        Global.changeScene(loginPage, "loginAdmin");
    }
}
