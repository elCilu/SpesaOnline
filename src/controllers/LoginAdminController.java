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
        String credential = emailField.getText();
        String password = passwordField.getText();

        try {
            if (credential.trim().isEmpty() || !isValidEmail(credential)) {
                setActionLabel("Email non valida");
                return;
            }

            if (password.isEmpty()) {
                setActionLabel("Campo password vuoto");
                return;
            }

            CredentialModel credentials = CredentialDao.selectByCredential(credential);
            if (credentials != null) {
                switch (entityCombo.getSelectionModel().getSelectedItem()) {
                    case "Corriere":

                        if (checkCredentialType(credentials, password, 4)) {
                            Global.USER_ID = ExpressDao.selectIdByEmail(credential);
                            goToAdminPage("Corriere");
                        } else {
                            setActionLabel("Credenziali errate");
                        }
                        break;
                    case "Fornitore":
                        if (checkCredentialType(credentials, password, 3)) {
                            Global.USER_ID = SupplierDao.selectIdByEmail(credential);
                            goToAdminPage("Fornitore");
                        } else {
                            setActionLabel("Credenziali errate");
                        }
                        break;
                    case "Magazziniere":
                        if (checkCredentialType(credentials, password, 2)) {
                            Global.USER_ID = StockManDao.selectIdByEmail(credential);
                            goToAdminPage("Magazziniere");
                        } else {
                            setActionLabel("Credenziali errate");
                        }
                        break;
                    case "Responsabile":
                        if (checkCredentialType(credentials, password, 1)) {
                            Global.USER_ID = ManagerDao.selectIdByEmail(credential);
                            goToAdminPage("Responsabile");
                        } else {
                            setActionLabel("Credenziali errate");
                        }
                        break;
                    default:
                        setActionLabel("Seleziona ruolo");
                }
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

    private void setActionLabel(String msg) {
        actionLabel.setText(msg);
        actionLabel.setVisible(true);
    }

    private boolean checkCredentialType(CredentialModel credentials, String password, int type){
        return CredentialUtil.checkPassword(password, credentials.getSalt(), credentials.getHash()) && credentials.getType() == type;
    }
}