package controllers;

import dao.FidelityCardDao;
import dao.ShoppingDao;
import enums.PaymentMethod;
import enums.Status;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ProductModel;
import models.ShoppingModel;
import sample.Global;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.*;

public class CheckOutController {

    public ShoppingModel shopping;
    @FXML
    private VBox imgs;
    @FXML
    private VBox name;
    @FXML
    private VBox qty;

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
    private int mod;
    @FXML
    private VBox price;
    private int idClient;


    @FXML
    protected void goToCart() {
        try {
            Stage stage = (Stage) CheckOutPage.getScene().getWindow();
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("../views/cart.fxml"));

            //load the parent
            Loader.load();
            CartController cart = Loader.getController();

            //opening cart age
            cart.setUpCart();

            stage.setScene(new Scene(Loader.getRoot()));
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

            //opening confirmed page
            confirmed.addProducts(shopping);


            stage.setScene(new Scene(Loader.getRoot()));
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCart(int mod) {
        if (mod == 1)
            this.mod = 4;
        if (mod == 2)
            this.mod = 2;
        this.idClient = Global.USER_ID;

        totSpesa.setText(String.format("%.2f", subTotal()));
        puntiSpesa.setText(String.format("%02d", (int) subTotal()));

        for (ProductModel p : Global.cart.keySet()) {
            //product image
            ImageView img = new ImageView();

            img.setImage(new Image(Global.IMG_PATH + "prod_" + String.format("%02d", p.getId()) +  ".jpg"));
            img.setFitHeight(70);
            img.setFitWidth(120);
            imgs.getChildren().add(img);

            //product name & code
            Text prodNameCode = new Text();
            prodNameCode.setText(p.getName() + " " + p.getBrand() + ((p.getQtyPack() != 1) ? ", " + p.getQtyPack() + "g" : ""));
            name.getChildren().add(prodNameCode);

            //product total price
            Text prodPrice = new Text();
            productTotalPrice(prodPrice, p);
            price.getChildren().add(prodPrice);

            //product quantity
            Text prodQty = new Text();
            prodQty.setText(String.format("%d", Global.cart.get(p)));

            qty.getChildren().add(prodQty);
        }
    }

    private void productTotalPrice(Text prodPrice, ProductModel p) {
        prodPrice.setText(String.format("€%.2f", p.getprice() * Global.cart.get(p)));
    }

    public void addShopping() {
        Date purchaseDate;
        LocalDate deliveryLocalDate;
        Date deliveryDate;
        Date tempDate;
        String deliveryH;
        float totalCost = subTotal();
        int earnedPoints = (int) totalCost;
        int status = 0;
        int paymentMethod;
        ZoneId defaultZoneId = ZoneId.systemDefault();

        try {
            purchaseDate = new Date();
            Calendar calendar = getInstance();
            calendar.setTime(purchaseDate);
            int tempDay = calendar.get(DAY_OF_MONTH) + mod;

            tempDate = new GregorianCalendar(calendar.get(YEAR), calendar.get(MONTH), tempDay).getTime();

            if (dataConsegna.getValue() == null) {
                actionTarget.setText("Seleziona una data di consegna");
                throw new Exception("Data di consegna non selezionata");
            }

            deliveryLocalDate = dataConsegna.getValue();
            deliveryDate = Date.from(deliveryLocalDate.atStartOfDay(defaultZoneId).toInstant());

            if (tempDate.after(deliveryDate)) {
                actionTarget.setText("Seleziona una data di consegna valida \n per la spedizione da te scelta");
                throw new Exception("Data di consegna non valida");
            }

            //Verifica orario consegna selezionato
            if (mattina.isSelected()) {
                deliveryH = "Mattina";
            } else if (pomeriggio.isSelected()) {
                deliveryH = "Pomeriggio";
            } else {
                actionTarget.setText("Seleziona orario di consegna");
                throw new Exception("Orario di consegna non selezionato");
            }

            //verifica metodo di pagamento selezionato
            if (creditCardRadio.isSelected()) {
                paymentMethod = 1;
            } else if (paypalRadio.isSelected()) {
                paymentMethod = 2;
            } else if (cashRadio.isSelected()) {
                paymentMethod = 3;
            } else {
                actionTarget.setText("Seleziona metodo di pagamento");
                throw new Exception("Metodo pagamento non selezionato");
            }

            // inserisco la spesa nel db
            shopping = new ShoppingModel(0, purchaseDate, deliveryDate, deliveryH, totalCost, earnedPoints, Status.values()[status], idClient, PaymentMethod.values()[paymentMethod]);
            int resultQuery = ShoppingDao.insertShopping(shopping);
            updatePoints(idClient, (FidelityCardDao.getPoints(idClient) + earnedPoints));

            if (resultQuery != 0) {
                shopping.setId(ShoppingDao.selectLast());
                goToConfirmed();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updatePoints(int id, int qty){
        try {
            int resultQuery = FidelityCardDao.updateQuantity(id, qty);
            if (resultQuery == 0)
                throw new Exception("Errore nell'aggiornamenti dei punti fedelta'");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private float subTotal(){
        float tot = 0;

        for(ProductModel p: Global.cart.keySet())
            tot += p.getprice() * Global.cart.get(p);

        return tot;
    }

}