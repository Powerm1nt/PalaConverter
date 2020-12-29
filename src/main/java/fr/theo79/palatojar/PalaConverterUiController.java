package fr.theo79.palatojar;

import fr.theo79.palatojar.ActionType;
import fr.theo79.palatojar.PalaConverterUi;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class PalaConverterUiController implements Initializable {

    @FXML
    public Text aboutTxt;

    private ActionType defaultAction = ActionType.DECRYPT;

    @FXML
    private Button chooseFile = new Button();

    @FXML
    private ComboBox<String> chooseAction = new ComboBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseAction.getItems().addAll("ENCRYPT","DECRYPT");
        chooseAction.getSelectionModel().select(0);
        chooseAction.setStyle("-fx-font-size: 12.0; -fx-font-weight: bold;");
        chooseAction.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("ENCRYPT")) setDefaultAction(ActionType.ENCRYPT);
            else setDefaultAction(ActionType.DECRYPT);
        });

        AtomicReference<String> textOldValue = new AtomicReference<>();
        String textNewValue;
        chooseFile.textProperty().addListener((observable, oldValue, newValue) -> textOldValue.set(oldValue));


        Task<Void> waitTime = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Platform.runLater(() -> {
                    chooseFile.setStyle("-fx-background-color: #19c800;");
                    chooseFile.setText("Done ✅");
                });


                Thread.sleep(2000);
                return null;
            }
        };

        waitTime.setOnSucceeded(event -> {
            chooseFile.setText(textOldValue.get());
            chooseFile.setStyle(chooseFile.getStyle().replace("-fx-background-color: #19c800;", ""));
            chooseFile.setDisable(false);
            chooseAction.setDisable(false);
        });

        chooseFile.addEventHandler(ActionEvent.ACTION, e -> {
            chooseFile.setDisable(true);
            chooseAction.setDisable(true);
            if (PalaConverterUi.openFile(getDefaultAction())) new Thread(waitTime).start();
            else {
                chooseFile.setDisable(false);
                chooseAction.setDisable(false);
            }
        });


        chooseFile.setStyle("-fx-font-size: 13.0;");
        chooseFile.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> chooseFile.setStyle(chooseFile.getStyle() + "-fx-font-weight: bold; -fx-background-color: #ff2c2c;"));
        chooseFile.addEventHandler(MouseEvent.MOUSE_EXITED, e -> chooseFile.setStyle(chooseFile.getStyle().replace("-fx-font-weight: bold; -fx-background-color: #ff2c2c;", "")));
        chooseFile.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> chooseFile.setStyle(chooseFile.getStyle() + "-fx-underline: true;"));
        chooseFile.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> chooseFile.setStyle(chooseFile.getStyle().replace("-fx-underline: true;", "")));

        aboutTxt.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> aboutTxt.setUnderline(true));
        aboutTxt.addEventHandler(MouseEvent.MOUSE_EXITED, e -> aboutTxt.setUnderline(false));
        aboutTxt.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                Desktop.getDesktop().browse(URI.create("https://discord.gg/6fjMehXxKx"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


    private ActionType getDefaultAction() {
        return defaultAction;
    }

    private void setDefaultAction(ActionType defaultAction) {
        this.defaultAction = defaultAction;
    }
}