package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class MenuController {

    @FXML
    public ToggleGroup MenuGroup;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private MainController mainController;

    private static final String BUILT_ITEM_FXML = "/fxml/BuiltItems.fxml";
    private static final String BUILT_ITEM_ADD_FXML = "/fxml/AddItem.fxml";
    private static final String CATEGORY_FXML = "/fxml/Category.fxml";
    private static final String STATISTICS_FXML = "/fxml/Statitics.fxml";
    private static final String ADD_CLIENT_FXML = "/fxml/Client.fxml";
    private static final String ORDERS_FXML = "/fxml/Orders.fxml";
    private static final String INFORM_CLIENT_FXML = "/fxml/InformClient.fxml";
    private static final String HISTORY_FXML = "/fxml/History.fxml";


    public void Statistics() {
       mainController.setLocation(STATISTICS_FXML);

    }

    public void AddClient() {
        mainController.setLocation(ADD_CLIENT_FXML);

    }

    public void AddCategory() {
        mainController.setLocation(CATEGORY_FXML);
    }

    public void openBuiltItems() {
        mainController.setLocation(BUILT_ITEM_FXML);

    }

    public void openOrders() {
        mainController.setLocation(ORDERS_FXML);
    }

    public MainController getMainController() {
        return mainController;
    }


    public void informClient() {
    mainController.setLocation(INFORM_CLIENT_FXML);}


    public void History() {
        mainController.setLocation(HISTORY_FXML);
    }
}
