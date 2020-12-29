package fr.theo79.palatojar.ui.controllers;

import fr.theo79.palatojar.ActionType;
import fr.theo79.palatojar.ui.PalaConverterUi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PalaConverterUiController implements Initializable {

    private ActionType defaultAction = ActionType.DECRYPT;

    @FXML
    private Button chooseFile = new Button();

    @FXML
    private ComboBox<String> chooseAction = new ComboBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseAction.getItems().addAll("DECRYPT","ENCRYPT");
        chooseAction.getSelectionModel().select(0);
        chooseAction.setStyle("-fx-font-size: 12.0; -fx-font-weight: bold;");
        chooseAction.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("ENCRYPT")) setDefaultAction(ActionType.ENCRYPT);
            else setDefaultAction(ActionType.DECRYPT);
        });

        chooseFile.addEventHandler(ActionEvent.ACTION, e -> {
            chooseFile.setDisable(true);
            chooseAction.setDisable(true);
            PalaConverterUi.openFile(getDefaultAction());
            chooseFile.setDisable(false);
            chooseAction.setDisable(false);
        });
        chooseFile.setStyle("-fx-font-size: 13.0;");
        chooseFile.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> chooseFile.setStyle(chooseFile.getStyle() + "-fx-font-weight: bold; -fx-background-color: #ff2c2c;"));
        chooseFile.addEventHandler(MouseEvent.MOUSE_EXITED, e -> chooseFile.setStyle(chooseFile.getStyle().replace("-fx-font-weight: bold; -fx-background-color: #ff2c2c;", "")));
        chooseFile.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> chooseFile.setStyle(chooseFile.getStyle() + "-fx-underline: true;"));
        chooseFile.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> chooseFile.setStyle(chooseFile.getStyle().replace("-fx-underline: true;", "")));
    }


    private ActionType getDefaultAction() {
        return defaultAction;
    }

    private void setDefaultAction(ActionType defaultAction) {
        this.defaultAction = defaultAction;
    }
}