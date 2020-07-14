package controllers;

import com.jfoenix.controls.JFXComboBox;
import dao.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.CredentialModel;
import sample.Global;
import utils.CredentialUtil;

import static utils.StringUtil.isValidEmail;

public class LoginAdminController {

    @FXML
    private AnchorPane loginAdminPage;
    @FXML
    private Label actionLabel;
    @FXML
    private JFXComboBox<String> entityCombo;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    private void login() {
        boolean logged = false;

        String credential = emailField.getText();
        String password = passwordField.getText();

        try {
            if (credential.trim().isEmpty() || !isValidEmail(credential)) {
                actionLabel.setText("Email non valida");
                actionLabel.setVisible(true);
                return;
            }

            if (password.isEmpty()) {
                actionLabel.setText("Campo password vuoto");
                actionLabel.setVisible(true);
                return;
            }
            CredentialModel credentials = CredentialDao.selectByCredential(credential);
            if (credentials != null) {

                if (CredentialUtil.checkPassword(password, credentials.getSalt(), credentials.getHash())) {
                    logged = true;
                    switch (entityCombo.getSelectionModel().getSelectedItem()) {
                        case "Corriere":

                            Global.USER_ID = ExpressDao.selectIdByEmail(credential);
                            goToAdminPage("Corriere");
                            break;
                        case "Fornitore":

                            Global.USER_ID = SupplierDao.selectIdByEmail(credential);
                            goToAdminPage("Fornitore");
                            break;
                        case "Magazziniere":

                            Global.USER_ID = StockManDao.selectIdByEmail(credential);
                            goToAdminPage("Magazziniere");
                            break;
                        case "Responsabile":

                            Global.USER_ID = ManagerDao.selectIdByEmail(credential);
                            goToAdminPage("Responsabile");
                            break;
                    }
                }
            }
            if (!logged) {
                actionLabel.setText("Credenziali errate");
                actionLabel.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAdminPage(String role) {
        switch (role){
            case "Corriere":
                Global.changeScene(loginAdminPage, "express");
                break;
            case "Fornitore":
                Global.changeScene(loginAdminPage, "supplier");
                break;
            case "Magazziniere":
                Global.changeScene(loginAdminPage, "stockMan");
                break;
            case "Responsabile":
                Global.changeScene(loginAdminPage, "manager");
                break;
        }
    }

    @FXML
    private void goToCustomerLogin() {
        Global.changeScene(loginAdminPage, "loginCustomer");
    }
}