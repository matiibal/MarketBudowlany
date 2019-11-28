package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.Dialogs;
import utils.FxmlUtils;

import java.util.Optional;


public class MainController {


    @FXML
    private CheckMenuItem alwaysOnTop;
    @FXML
    private BorderPane borderPane;
    //jezeli dołaczasz nowy plik to aby go uchwycić należy dodać Controller do nazwy
    @FXML
    private MenuController menuController;




    @FXML
    public void initialize() {
      menuController.setMainController(this);

    }


    public void setLocation(String fxmlPath) {
        Pane pane = new Pane();
        pane = FxmlUtils.fxmlLoader(fxmlPath);
        borderPane.setCenter(pane);
    }
    public void setAlwaysOnTop(String fxmlPath)
    {
        borderPane.setCenter(FxmlUtils.fxmlLoader(fxmlPath));

    }
    public void setAddItemLocation(String fxmlPath)
    {
        borderPane.setLayoutY(150);
        borderPane.setLayoutY(150);
        setLocation(fxmlPath);
    }

    public void setMainController(MenuController menuController) {
    }
}
