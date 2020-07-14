package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import dao.ClientDao;
import dao.CredentialDao;
import dao.FidelityCardDao;
import enums.PaymentMethod;
import javafx.scene.control.Label;
import models.FidelityCard;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import models.ClientModel;
import models.CredentialModel;
import sample.Global;
import utils.CredentialUtil;
import utils.StringUtil;

import java.util.Calendar;

import static utils.StringUtil.*;

public class SignUpController {

    @FXML
    private AnchorPane signUpPage;
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
    private ToggleGroup paymentMethodGroup;
    @FXML
    private Label actionLabel;
    @FXML
    private JFXToggleButton fidelityCardToggle;
    @FXML
    private JFXButton signUpButton;


    @FXML
    private void signUp() {
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
                actionLabel.setText("Tutti i campi sono obbligatori");
                actionLabel.setVisible(true);
                throw new Exception("Campi del sign up vuoti");
            } else {
                if (!isValidName(name)) {
                    actionLabel.setText("Nome deve essere composto solo da lettere");
                    actionLabel.setVisible(true);
                    throw new Exception("Nome non corretto");
                } else if (!isValidSurname(surname)){
                    actionLabel.setText("Cognome deve essere composto solo da lettere");
                    actionLabel.setVisible(true);
                    throw new Exception("Cognome non corretto");
                } else if (!isValidZip(zip)) {
                    actionLabel.setText("CAP non valido");
                    actionLabel.setVisible(true);
                    throw new Exception("CAP non corretto");
                } else if (!isValidPhone(phoneNumber)) {
                    actionLabel.setText("Numero non valido");
                    actionLabel.setVisible(true);
                    throw new Exception("Numero non corretto");
                } else if (!isValidEmail(email)) {
                    actionLabel.setText("Email non valida");
                    actionLabel.setVisible(true);
                    throw new Exception("Email non corretta");
                } else if (password.length() < 8) {
                    actionLabel.setText("Password minimo 8 caratteri");
                    actionLabel.setVisible(true);
                    throw new Exception("Password non valida");
                } else if (!password.equals(confirmedPassword)) {
                    actionLabel.setText("Le password devono essere uguali");
                    actionLabel.setVisible(true);
                    throw new Exception("Password non uguali");
                }
            }

            JFXRadioButton selectedRadio = (JFXRadioButton) paymentMethodGroup.getSelectedToggle();
            switch (selectedRadio.getText()) {
                case "Carta di credito" :
                    paymentMethod = 0;
                    break;
                case "Paypal":
                    paymentMethod = 1;
                    break;
                case "Alla consegna":
                    paymentMethod = 2;
                    break;
                default:
                    actionLabel.setText("Seleziona metodo di pagamento");
                    actionLabel.setVisible(true);
                    throw new Exception("Metodo pagamento non selezionato");
            }

            if (CredentialDao.selectByCredential(email) != null) {
                actionLabel.setText("Email già in uso");
                actionLabel.setVisible(true);
                throw new Exception("Email già presente nella tabella");
            }

            int resultQuery = ClientDao.insertClient(new ClientModel(
                            0,
                            StringUtil.formatName(name),
                            StringUtil.formatName(surname),
                            address,
                            zip,
                            phoneNumber,
                            email,
                            PaymentMethod.values()[paymentMethod]
                    )
            );

            if (resultQuery != 0) {
                byte[] salt = CredentialUtil.createSalt();
                CredentialModel credential = new CredentialModel(
                        0,
                        email,
                        CredentialUtil.generateHash(password, salt),
                        salt,
                        0
                );
                resultQuery = CredentialDao.insertCredentials(credential);
                if (fidelityCardToggle.isSelected()) {
                    FidelityCard fidelityCard = new FidelityCard(
                            0,
                            Calendar.getInstance().getTime(),
                            0,
                            ClientDao.selectIdByEmail(email));

                    resultQuery += FidelityCardDao.insertCard(fidelityCard);
                }
            }
            if (resultQuery != 0) {
                backToLogin();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void setSignUpButton() {
        signUpButton.setDisable(!signUpButton.isDisable());
    }

    @FXML
    private void backToLogin() {
        Global.changeScene(signUpPage, "loginCustomer");
    }
}
