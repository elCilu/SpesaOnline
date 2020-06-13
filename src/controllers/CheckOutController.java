package controllers;

import dao.ProductShoppingDao;
import dao.ShoppingDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.CartModel;
import models.ProductShoppingModel;
import models.ShoppingModel;

import java.util.Date;

public class CheckOutController {

    @FXML
    private SplitPane CheckOutPage;
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
    private Text totSpesa;
    @FXML
    private Text puntiSpesa;

    public ToggleGroup paymentMethodGroup;
    public ToggleGroup delivery;
    private CartModel cart;
    private int mod;


    @FXML
    protected void goToCart() {
        try {
            Stage stage = (Stage) CheckOutPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/cart.fxml"));
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void goToConfirmed() {
        try {
            Stage stage = (Stage) CheckOutPage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../views/confirmed.fxml"));
            stage.setScene(new Scene(root, 400, 350));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCart(CartModel cart, int mod){
        this.cart = cart;
        this.mod = mod;
        totSpesa.setText(String.format("%.2f",cart.subTotal()));
        puntiSpesa.setText(String.format("%02d",cart.getPoints()));
    }


    public void addShopping() {
        Date purchaseDate;
        Date deliveryDate;
        int deliveryH;
        int totalCost = 0;
        int earnedPoints = 0;
        int status = 0;
        int idClient = 0;
        int paymentMethod;

        try{

            purchaseDate = null;//dataConsegna.getValue();
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
                actionTarget.setText("Seleziona orario di consegna");
                throw new Exception("Orario di consegna non selezionato");
            }

            /*for()

            addProductShopping();

            * */

            int resultQuery = 0;
            ShoppingDao.insertShopping(new ShoppingModel(0, purchaseDate, deliveryDate, totalCost, earnedPoints, status, idClient, paymentMethod));

            if (resultQuery != 0){
                goToConfirmed();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addProductShopping(int idProduct, int idShopping, int qty) {
        idProduct = 0;
        idShopping = 0;
        qty = 1;

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