package controllers;

import dao.ClientDao;
import dao.PasswordDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.ClientModel;
import utils.PasswordUtil;

public class SignUpController {

    @FXML
    private GridPane signupPage;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField zipField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private RadioButton creditCardRadio;
    @FXML
    private RadioButton paypalRadio;
    @FXML
    private RadioButton cashRadio;
    @FXML
    private Button signUpButton;
    @FXML
    private Button backButton;

    public void signUp(ActionEvent actionEvent) {
        int paymentMethod;

        //TODO: select just one radio button
        if (creditCardRadio.isSelected())
            paymentMethod = 1;
        else if (paypalRadio.isSelected())
            paymentMethod = 2;
        else
            paymentMethod = 3;

        byte[] salt = PasswordUtil.createSalt();

        //TODO: first save user, that password
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            PasswordDao.insertPassword(PasswordUtil.generateHash(passwordField.getText(), salt), salt);
        }

        String name = nameField.getText();
        String surname = surnameField.getText();
        String address = addressField.getText();
        String zip = zipField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        int passwordId = PasswordDao.getIdBySalt(salt);

        ClientModel client = new ClientModel(0,
                name,
                surname,
                address,
                zip,
                phoneNumber,
                email,
                paymentMethod,
                passwordId);

        ClientDao.insertUser(client);
    }

    public void backToLogin(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) signupPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
            stage.setScene(new Scene(root, 300, 275));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
