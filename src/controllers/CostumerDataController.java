package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ClientModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utils.StringUtil.*;

public class CostumerDataController {
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField zipField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private GridPane costumerData;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Text actionTarget;

    private static ClientModel client;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    //carico i dati iniziali sulla pagina
    private void loadData() {

        client = new ClientModel();
        ClientModel c = new ClientModel(102, "Sara", "Beschi", "Via Danilo Guidetti", "46043", "3333333333", "sara@gmail.com", 1);
        client.addToClient(c);
    }


    public void Modifica() {

        String name;
        String surname;
        String address;
        String zip;
        String phoneNumber;
        String email;
        String password;

        try {
            Stage stage = (Stage) costumerData.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/costumerData.fxml"));
            stage.setScene(new Scene(root, 600, 275));
            stage.show();

            name = nameField.getText();
            surname = surnameField.getText();
            address = addressField.getText();
            zip = zipField.getText();
            phoneNumber = phoneNumberField.getText();
            email = emailField.getText();
            password = passwordField.getText();

            actionTarget.setText(name);
            actionTarget.setText(surname);
            actionTarget.setText(address);
            actionTarget.setText(zip);
            actionTarget.setText(phoneNumber);
            actionTarget.setText(email);
            actionTarget.setText(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Salva() {
        String name;
        String surname;
        String address;
        String zip;
        String phoneNumber;
        String email;
        String password;

        try {
            Stage stage = (Stage) costumerData.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/costumerData.fxml"));
            stage.setScene(new Scene(root, 600, 275));
            stage.show();

            name = nameField.getText();
            surname = surnameField.getText();
            address = addressField.getText();
            zip = zipField.getText();
            phoneNumber = phoneNumberField.getText();
            email = emailField.getText();
            password = passwordField.getText();

            if (name.trim().isEmpty() || surname.trim().isEmpty() || address.trim().isEmpty()
                    || zip.trim().isEmpty() || phoneNumber.trim().isEmpty() || email.trim().isEmpty()
                    || password.isEmpty()) {
                actionTarget.setText("Tutti i campi sono obbligatori");
                throw new Exception("Campi del sign up vuoti");
            } else {
                if (!isValidName(name)) {
                    actionTarget.setText("Nome deve essere composto solo da lettere");
                    throw new Exception("Nome non corretto");
                } else if (!isValidSurname(surname)) {
                    actionTarget.setText("Cognome deve essere composto solo da lettere");
                    throw new Exception("Cognome non corretto");
                } else if (!isValidZip(zip)) {
                    actionTarget.setText("CAP non valido");
                    throw new Exception("CAP non corretto");
                } else if (!isValidPhone(phoneNumber)) {
                    actionTarget.setText("Numero non valido");
                    throw new Exception("Numero non corretto");
                } else if (!isValidEmail(email)) {
                    actionTarget.setText("Email non valida");
                    throw new Exception("Email non corretta");
                } else if (password.length() < 8) {
                    actionTarget.setText("Password minimo 8 caratteri");
                    throw new Exception("Password non valida");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
