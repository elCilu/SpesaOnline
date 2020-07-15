package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.ProductModel;
import utils.OSUtil;

import java.io.File;
import java.util.SortedMap;
import java.util.TreeMap;

public class Global {
    public static int USER_ID;
    public static String OS = System.getProperty("os.name").toLowerCase();
    public static SortedMap<ProductModel, Integer> cart = new TreeMap<>();
    public static final String IMG_PATH = setPath();

    protected static String setPath(){
        String path = "";
        if(OSUtil.isWindows())
            path = "file:\\" + new File("").getAbsolutePath() + "\\images\\";

        if(OSUtil.isUnix())
            path = "file://" + new File("").getAbsolutePath() + "/images/";
        return path;
    }

    public static void changeScene(Pane pane, String page){
        try {
            Stage stage = (Stage) pane.getScene().getWindow();
            Parent root = FXMLLoader.load(Global.class.getResource("../views/" + page + ".fxml"));
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
