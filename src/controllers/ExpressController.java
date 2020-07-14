package controllers;

import dao.ClientDao;
import dao.ExpressDao;
import dao.ShoppingDao;
import enums.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.ClientModel;
import models.ShoppingModel;
import sample.Global;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ExpressController implements Initializable {

    @FXML
    public Label nameLabel, pivaLabel;
    @FXML
    public ScrollPane bodyConsegne;
    @FXML
    private VBox infoSpesa, checkSpesa;

    List<ShoppingModel> speseOdierne = ShoppingDao.getTodayDelivery();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    public void loadData() {
        nameLabel.setText(ExpressDao.selectNameById(Global.USER_ID));
        pivaLabel.setText(ExpressDao.selectPivaById(Global.USER_ID));

        for(ShoppingModel s : speseOdierne){
            Text info = new Text();
            ClientModel c = ClientDao.selectById(s.getIdClient());
            info.setText(String.format("Spesa %d, %s %s %s %s", s.getId(), c.getName(), c.getSurname(), c.getAddress(), s.getDeliveryH()));
            infoSpesa.getChildren().add(info);

            CheckBox check = new CheckBox();
            check.setText("Consegnata");
            checkSpesa.getChildren().add(check);

            check.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if(check.isSelected()) {
                        s.setStatus(Status.DELIVERED);
                        updateShoppingStatus(s.getId(), s.getStatus().ordinal());
                    }
                    check.setDisable(true);
                }
            });

        }
    }

    public void updateShoppingStatus(int idShopping, int status){
        try {
            int resultQuery = ShoppingDao.updateStatus(idShopping, status);
            if (resultQuery == 0)
                throw new Exception("Errore nell'aggiornamento dello stato dello shopping nel db");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
