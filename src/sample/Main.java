package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //TODO: think how to display errors (System.err, Log...) in the whole application

    @Override
    public void init() throws Exception {
        //TODO DBSeeder
        //TODO create db connection
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        primaryStage.setTitle("Spesa Online");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        //TODO close db connection
        super.stop();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
