package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("MainWindow.fxml"));
        Parent mainWindow = mainLoader.load();

        MainWindowController mainWindowController = mainLoader.getController();

        primaryStage.setTitle("Main Window");
        primaryStage.setScene(new Scene(mainWindow, 900, 750));
        primaryStage.show();

        FXMLLoader secondLoader = new FXMLLoader();
        secondLoader.setLocation(getClass().getResource("SelectWindow.fxml"));
        Parent secondWindow = secondLoader.load();
        SelectWindowController selectWindowController = secondLoader.getController();
        selectWindowController.setMainWindowController(mainWindowController);

        Stage secondStage = new Stage();
        secondStage.setTitle("Select Window");
        secondStage.setScene(new Scene(secondWindow, 500, 500));
        secondStage.initModality(Modality.WINDOW_MODAL);
        secondStage.initOwner(primaryStage);
        secondStage.show();

    }
}
