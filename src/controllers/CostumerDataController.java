package controllers;

import dao.ClientDao;
import dao.CredentialDao;
import enums.PaymentMethod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ClientModel;
import models.CredentialModel;
import sample.Global;
import utils.CredentialUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static utils.StringUtil.*;

public class CostumerDataController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField zipField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private GridPane costumerData;
    @FXML
    private TextField emailField;
    @FXML
    private Label passwordLabel;
    @FXML
    private HBox modifyPasswordButton;
    @FXML
    private Label newPasswordLabel;
    @FXML
    private TextField newPasswordField;
    @FXML
    private Label passwordCheckLabel;
    @FXML
    private TextField passwordCheckField;
    @FXML
    private ToggleGroup paymentMethodGroup;
    @FXML
    private RadioButton creditCardRadio;
    @FXML
    private RadioButton paypalRadio;
    @FXML
    private RadioButton cashRadio;
    @FXML
    private Text actionTarget;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    //carico i dati iniziali sulla pagina
    private void loadData() {
        ClientModel client;
        try {
            client = ClientDao.selectById(Global.USER_ID);

            if (client != null) {
                nameField.setText(client.getName());
                surnameField.setText(client.getSurname());
                addressField.setText(client.getAddress());
                zipField.setText(client.getZip());
                phoneNumberField.setText(client.getPhoneNumber());
                emailField.setText(client.getEmail());

                if (client.getIdPaymentMethod() == PaymentMethod.CREDIT_CARD) {
                    paymentMethodGroup.selectToggle(creditCardRadio);
                } else if (client.getIdPaymentMethod() == PaymentMethod.PAYPAL) {
                    paymentMethodGroup.selectToggle(paypalRadio);
                } else {
                    paymentMethodGroup.selectToggle(cashRadio);
                }
                creditCardRadio.setDisable(true);
                paypalRadio.setDisable(true);
                cashRadio.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modify() {
        try {
            nameField.setDisable(false);
            surnameField.setDisable(false);
            addressField.setDisable(false);
            zipField.setDisable(false);
            phoneNumberField.setDisable(false);
            passwordLabel.setVisible(true);
            modifyPasswordButton.setVisible(true);
            creditCardRadio.setDisable(false);
            paypalRadio.setDisable(false);
            cashRadio.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifyPassword() {
        newPasswordLabel.setVisible(true);
        newPasswordField.setVisible(true);
        passwordCheckLabel.setVisible(true);
        passwordCheckField.setVisible(true);
    }

    @FXML
    private void save() {
        String name;
        String surname;
        String address;
        String zip;
        String phoneNumber;
        String email;
        String newPassword;
        String checkPassword;
        PaymentMethod paymentMethod;

        try {

            name = nameField.getText();
            surname = surnameField.getText();
            address = addressField.getText();
            zip = zipField.getText();
            phoneNumber = phoneNumberField.getText();
            email = emailField.getText();
            newPassword = newPasswordField.getText();
            checkPassword = passwordCheckField.getText();

            if (name.trim().isEmpty() || surname.trim().isEmpty() || address.trim().isEmpty()
                    || zip.trim().isEmpty() || phoneNumber.trim().isEmpty() || email.trim().isEmpty()) {
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
                }
            }

            boolean changePassword = false;

            if (!newPassword.isEmpty()) {
                if (newPassword.length() < 8) {
                    actionTarget.setText("Password minimo 8 caratteri");
                    throw new Exception("Password non valida");
                } else if (!newPassword.equals(checkPassword)) {
                    actionTarget.setText("Le password devono essere uguali");
                    throw new Exception("Password non uguali");
                }
                changePassword = true;
            }

            RadioButton buttonSelected = (RadioButton) paymentMethodGroup.getSelectedToggle();

            if (buttonSelected.equals(creditCardRadio)) {
                paymentMethod = PaymentMethod.CREDIT_CARD;
            } else if (buttonSelected.equals(paypalRadio)) {
                paymentMethod = PaymentMethod.PAYPAL;
            } else {
                paymentMethod = PaymentMethod.ON_DELIVERY;
            }

            int updated = ClientDao.updateById(new ClientModel(
                    Global.USER_ID,
                    name,
                    surname,
                    address,
                    zip,
                    phoneNumber,
                    email,
                    paymentMethod));

            if (updated != 0) {
                if (changePassword) {
                    byte[] salt = CredentialUtil.createSalt();

                    CredentialModel credentials = CredentialDao.selectByCredential(email);

                    credentials.setHash(CredentialUtil.generateHash(newPassword, salt));
                    credentials.setSalt(salt);

                    updated = CredentialDao.updatePasswordByEmail(credentials);
                    //TODO: to clean code
                    if (updated != 0) {
                        actionTarget.setText("Anagrafica modificata correttamente!");
                    } else {
                        actionTarget.setText("Errore durante la modifica dell'anagrafica.");
                    }
                }
                actionTarget.setText("Anagrafica modificata correttamente!");
            } else {
                actionTarget.setText("Errore durante la modifica dell'anagrafica.");
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

   /* public void visualizeShops(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) CostumerDataController.getScene().getWindow();
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("../views/confirmed.fxml"));

            //load the parent
            Loader.load();
            ConfirmedController confirmed = Loader.getController();

            //opening confirmed page
            confirmed.addProducts(shopping);


            stage.setScene(new Scene(Loader.getRoot()));
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

}
