package controllers;

import dao.ClientDao;
import dao.CredentialDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ClientModel;
import models.CredentialModel;
import utils.CredentialUtil;
import utils.StringUtil;

import static utils.StringUtil.*;

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
    private ToggleGroup paymentMethodGroup;
    @FXML
    private Button signUpButton;
    @FXML
    private Button backButton;
    @FXML
    private Text actionTarget;

    public void signUp() {
        String name;
        String surname;
        String address;
        String zip;
        String phoneNumber;
        String email;
        String password;
        String confirmedPassword;
        int paymentMethod;


        try{
            name = nameField.getText();
            surname = surnameField.getText();
            address = addressField.getText();
            zip = zipField.getText();
            phoneNumber = phoneNumberField.getText();
            email = emailField.getText();
            password = passwordField.getText();
            confirmedPassword = confirmPasswordField.getText();

            if (name.trim().isEmpty() || surname.trim().isEmpty() || address.trim().isEmpty()
                    || zip.trim().isEmpty() || phoneNumber.trim().isEmpty() || email.trim().isEmpty()
                    || password.isEmpty() || confirmedPassword.isEmpty()) {
                actionTarget.setText("Tutti i campi sono obbligatori");
                throw new Exception("Campi del sign up vuoti");
            } else {
                if (!isValidName(name)) {
                    actionTarget.setText("Nome deve essere composto solo da lettere");
                    throw new Exception("Nome non corretto");
                } else if (!isValidSurname(surname)){
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
                } else if (!password.equals(confirmedPassword)) {
                    actionTarget.setText("Le password devono essere uguali");
                    throw new Exception("Password non uguali");
                }
            }

            if (creditCardRadio.isSelected()) {
                paymentMethod = 1;
            }
            else if (paypalRadio.isSelected()) {
                paymentMethod = 2;
            }
            else if (cashRadio.isSelected()) {
                paymentMethod = 3;
            }
            else {
                actionTarget.setText("Seleziona metodo di pagamento");
                throw new Exception("Metodo pagamento non selezionato");
            }


            if (CredentialDao.selectByEmail(email) != null){
                actionTarget.setText("Email già in uso");
                throw new Exception("Email già presente nella tabella");
            }

            int resultQuery = ClientDao.insertClient(new ClientModel(0, StringUtil.formatName(name),
                    StringUtil.formatName(surname), address, zip, phoneNumber, email, paymentMethod));
            if (resultQuery != 0) {
                byte[] salt = CredentialUtil.createSalt();
                CredentialModel credential = new CredentialModel(0, email,
                        CredentialUtil.generateHash(password, salt), salt);
                resultQuery = CredentialDao.insertCredentials(credential);
            }
            if (resultQuery != 0){
                actionTarget.setText("Utente inserito correttamente");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backToLogin() {
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
