package fr.theo79.palatojar;

import fr.theo79.palatojar.ActionType;
import fr.theo79.palatojar.PalaConverter;
import fr.theo79.palatojar.PalaMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

public class PalaConverterUi extends Application {

    private static Stage defaultStage;
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        defaultStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/ui/PalaConverterUi.fxml"));
        mainScene = new Scene(root);
        mainScene.getStylesheets().add(getClass().getResource("/css/ui.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(PalaMain.getAppName()
                + " - "
                + PalaMain.getAppVersion()
                + " | "
                + System.getProperty("user.name"));

        primaryStage.show();
        primaryStage.centerOnScreen();

    }

    public static boolean openFile(ActionType actionType) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select the Pala-Mod File to " + actionType.toString().toLowerCase() + " ...");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter( actionType.equals(ActionType.DECRYPT) ? "Pala-Mod files " : "Jars Files",
                actionType.equals(ActionType.DECRYPT) ? "*.pala" : "*.jar"));

        File selectedFile = chooser.showOpenDialog(defaultStage);
        if (selectedFile != null) {
            try {
                new PalaConverter(selectedFile).aesEncoder(actionType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}
