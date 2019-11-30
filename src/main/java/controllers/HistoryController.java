package controllers;

import ModelFx.OrdersService;
import database.models.Orders;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import utils.HistoryData;

public class HistoryController {

    @FXML
    private TableView<Orders> historyOrder;
    @FXML
    private TableColumn<Orders, String> surnameColumn;
    @FXML
    private TableColumn<Orders, String> dateColumn;
    @FXML
    private TableColumn<Orders, String> nipColumn;
    @FXML
    private TableColumn<Orders, String> totalColumn;
    @FXML
    private TableColumn<Orders, String> deleteColumn;
    private OrdersService ordersService;

    public void initialize() {

    }


}
