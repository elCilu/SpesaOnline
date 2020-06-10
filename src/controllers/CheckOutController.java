package controllers;

import dao.ProductShoppingDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ProductShoppingModel;

import java.util.Date;

public class CheckOutController {

    @FXML
    private GridPane CheckOutPage;
    @FXML
    private DatePicker dataConsegna;
    @FXML
    private RadioButton mattina;
    @FXML
    private RadioButton pomeriggio;
    @FXML
    private RadioButton creditCardRadio;
    @FXML
    private RadioButton paypalRadio;
    @FXML
    private RadioButton cashRadio;
    @FXML
    private Text actionTarget;
    @FXML
    private TextField totSpesa;
    @FXML
    private TextField puntiSpesa;

    public ToggleGroup paymentMethodGroup;
    public ToggleGroup delivery;

    @FXML
    protected void goToCart() {
        try {
            Stage stage = (Stage) CheckOutPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/cart.fxml"));
            stage.setScene(new Scene(root, 400, 350));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addShopping() {
        Date purchaseDate;
        Date deliveryDate;
        int deliveryH;
        int totalCost;
        int earnedPoints;
        int status;
        int idClient;
        int paymentMethod;

        try{
            deliveryDate = null;


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

            if (mattina.isSelected()) {
                deliveryH = 1;
            }
            else if (pomeriggio.isSelected()) {
                deliveryH = 2;
            }
            else {
                actionTarget.setText("Seleziona metodo di pagamento");
                throw new Exception("Metodo pagamento non selezionato");
            }
            int resultQuery = 0; //ShoppingDao.insertShopping(new ShoppingModel(0,));

            if (resultQuery != 0){
                actionTarget.setText("Spesa aggiunta correttamente");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addProductShopping() {
        int idProduct = 0;
        int idShopping = 0;
        int qty = 1;

        try{
            int resultQuery = ProductShoppingDao.insertProductShopping(new ProductShoppingModel(0, idProduct,
                    idShopping, qty));

            if (resultQuery != 0){
                actionTarget.setText("Prodotto inserito correttamente");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}