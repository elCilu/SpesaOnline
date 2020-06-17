package sample;

import dao.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private boolean isCreatedPopulated;
    //TODO: think how to display errors (System.err, Log...) in the whole application

    @Override
    public void init() throws Exception {
        System.out.println("Opening application... ");
        isCreatedPopulated = DatabaseHandler.createAndPopulateTables();
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (isCreatedPopulated) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/cart.fxml"));
            primaryStage.setTitle("Spesa Online");
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
            primaryStage.show();

            System.out.println("Application opened!");
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("../views/errorOnStart.fxml"));
            primaryStage.setTitle("Spesa Online");
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
            primaryStage.show();
            System.out.println("Error during setting up database!");
        }
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Closing application...");
        DatabaseHandler.closeConnection();
        super.stop();
        System.out.println("Application closed.");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
