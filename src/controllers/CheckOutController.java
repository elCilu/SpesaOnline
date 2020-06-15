package controllers;

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
import models.ShoppingModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    public ShoppingModel shopping;


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
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("../views/confirmed.fxml"));

            //load the parent
            Loader.load();
            ConfirmedController confirmed = Loader.getController();

            //sending cart to the checkout page
            confirmed.addProducts(cart, shopping);


            stage.setScene(new Scene(Loader.getRoot()));
            stage.sizeToScene();
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
        LocalDate deliveryLocalDate;
        Date deliveryDate;
        Date tempDate;
        String deliveryH;
        float totalCost = cart.subTotal();
        int earnedPoints = cart.getPoints();
        int status = 0;
        int idClient = 1; //TODO:passaggio da cheikh
        int paymentMethod;
        ZoneId defaultZoneId = ZoneId.systemDefault();

        try{
            purchaseDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(purchaseDate);

            tempDate = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).getTime();
            if(dataConsegna.getValue() == null){
                actionTarget.setText("Seleziona una data di consegna");
                throw new Exception("Data di consegna non selezionata");
            }

            deliveryLocalDate = dataConsegna.getValue();
            deliveryDate = Date.from(deliveryLocalDate.atStartOfDay(defaultZoneId).toInstant());

            if(deliveryDate.before(purchaseDate)){
                actionTarget.setText("Seleziona una data di consegna valida \n per la spedizione da te scelta");
                throw new Exception("Data di consegna non valida");
            }


            //verifica metodo di pagamento selezionato
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

            //Verifica orario consegna selezionato
            if (mattina.isSelected()) {
                deliveryH = "Mattina";
            }
            else if (pomeriggio.isSelected()) {
                deliveryH = "Pomeriggio";
            }
            else {
                actionTarget.setText("Seleziona orario di consegna");
                throw new Exception("Orario di consegna non selezionato");
            }

            // inserisco la spesa nel db
            shopping = new ShoppingModel(0, purchaseDate, deliveryDate, deliveryH, totalCost, earnedPoints, status, idClient, paymentMethod);
            int resultQuery = ShoppingDao.insertShopping(shopping);

            if (resultQuery != 0){
                goToConfirmed();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}