package controllers;

import dao.ClientDao;
import dao.ShoppingDao;
import enums.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.ClientModel;
import models.ExpressModel;
import java.util.Date;
import java.util.List;
import models.ShoppingModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExpressController {

    @FXML
    public Label nameLabel, pivaLabel;
    @FXML
    public ScrollPane bodyConsegne;
    @FXML
    private VBox infoSpesa, checkSpesa;
    Date oggi = new Date();
    List<ShoppingModel> speseOdierne = ShoppingDao.getTodayDelivery(oggi);

    private void loadData() {
        nameLabel.setText("/IN FASE DI LOGIN*/.getCompanyName()");
        pivaLabel.setText("/IN FASE DI LOGIN*/.getpIva");

        for(ShoppingModel s : speseOdierne){
            Text info = new Text();
            ClientModel c = ClientDao.selectById(s.getIdClient());
            info.setText(String.format("Spesa %i, %s %s %s %s", s.getId(), c.getName(), c.getSurname(), c.getAddress(), s.getDeliveryH()));
            infoSpesa.getChildren().add(info);

            CheckBox check = new CheckBox();
            check.setText("Consegnata");
            checkSpesa.getChildren().add(check);

            check.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if(check.isSelected())
                        s.setStatus(Status.DELIVERED);
                }
            });

        }
    }



}
