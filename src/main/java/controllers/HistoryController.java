package controllers;

import ModelFx.BuiltItemFx;
import ModelFx.OrdersService;
import database.models.Orders;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Dialogs;
import utils.FxmlUtils;
import utils.HistoryData;
import utils.HistoryDataFx;

public class HistoryController {

    @FXML
    private TableView<HistoryDataFx> historyOrder;
    @FXML
    private TableColumn<HistoryDataFx, String> secondNameColumn;
    @FXML
    private TableColumn<HistoryDataFx, String> orderDateColumn;
    @FXML
    private TableColumn<HistoryDataFx, String> nipColumn;
    @FXML
    private TableColumn<HistoryDataFx, String> totalColumn;
    @FXML
    private TableColumn<HistoryDataFx, HistoryDataFx> deleteColumn;
    private OrdersService ordersService;
    @FXML
    private TextField searchOrder;

    public Button createNewButton(String source) {
        Button buttonAddItemToOrder = new Button();
        javafx.scene.image.Image image = new Image(this.getClass().getResource(source).toString());
        ImageView imageView = new ImageView(image);
        buttonAddItemToOrder.setGraphic(imageView);
        return buttonAddItemToOrder;
    }

    public void initialize() {

        ordersService = new OrdersService();
        this.ordersService.initHistoryData();
        this.historyOrder.setItems(ordersService.getHistoryDataOList());


        this.secondNameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameColumnProperty());
        this.orderDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateColumnProperty());
        this.nipColumn.setCellValueFactory(cellData -> cellData.getValue().nipColumnProperty());
        this.totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalColumnProperty());
        this.deleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));


        //ustawienie przycisku usuwania
        this.deleteColumn.setCellFactory(param -> new TableCell<HistoryDataFx, HistoryDataFx>() {
            Button button = createNewButton("/icons/delete.jpg");

            //tworzenie komórki
            protected void updateItem(HistoryDataFx item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event ->
                {

                });
            }
        });


        searchOrder();
    }


    public void searchOrder() {
        FilteredList<HistoryDataFx> filteredData = new FilteredList<>(this.ordersService.getHistoryDataOList(), b->true);
        this.searchOrder.textProperty().addListener((observable, oldValue, newValue) ->
        {
            filteredData.setPredicate(historyDataFx ->
                    {
                        if(newValue == null || newValue.isEmpty())
                        {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if(historyDataFx.getSurnameColumn().toLowerCase().contains(lowerCaseFilter))
                        {
                            return true;
                        }
                        else if(historyDataFx.getNipColumn().toLowerCase().contains(lowerCaseFilter))
                        {
                            return true;
                        }
                        else if(historyDataFx.getDateColumn().toLowerCase().contains(lowerCaseFilter))
                        {
                            return true;
                        }
                        else
                            return false;
                    }

            );
        });

        SortedList<HistoryDataFx> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(historyOrder.comparatorProperty());
       historyOrder.setItems(sortedData);
    }
}

