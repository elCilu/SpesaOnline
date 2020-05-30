package controllers;

import dao.ProductShoppingDao;
import dao.ShoppingDao;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.ProductShoppingModel;
import models.ShoppingModel;

import java.time.LocalDate;
import java.util.Date;

public class CheckOutController {

    @FXML
    private GridPane CheckOutPage;
    @FXML
    private DatePicker dataConsegna;
    @FXML
    private ComboBox orarioConsegna;
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

    public void addShopping() {
        Date purchaseDate;
        Date deliveryDate;
        int totalCost;
        int earnedPoints;
        int status;
        int idClient;
        int PaymentMethod;
        try{
            deliveryDate = null;


            if (creditCardRadio.isSelected()) {
                PaymentMethod = 1;
            }
            else if (paypalRadio.isSelected()) {
                PaymentMethod = 2;
            }
            else if (cashRadio.isSelected()) {
                PaymentMethod = 3;
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