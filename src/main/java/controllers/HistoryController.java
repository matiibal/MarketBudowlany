package controllers;


import ModelFx.OrdersService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.*;
import utils.converters.ConvertOrderDetails;

import java.util.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class HistoryController {

    @FXML
    private TableView<HistoryDataFx> historyOrder;
    @FXML
    private TableView<OrderDetailsFx> orderDetailsTableView;
    @FXML
    private TableColumn<OrderDetailsFx, String> nameColumn;
    @FXML
    private TableColumn<OrderDetailsFx, String> quantityColumn;
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
    @FXML
    private TableColumn<HistoryDataFx, HistoryDataFx> showDetails;
    private OrdersService ordersService;
    @FXML
    private TextField searchOrder;
    private OrderDetails orderDetails;

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
        this.showDetails.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        this.orderDetailsTableView.setItems(ordersService.getOrderDetailsFxObservableList());
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProductProperty());
        this.quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        orderDetailsTableView.setItems(ordersService.getOrderDetailsFxObservableList());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProductProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());



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
                    ordersService.initOrderInformation(item.getId());

                    ordersService.deleteOrder(item.getId());
                    ordersService.getHistoryDataOList().remove(item);
                    orderDetailsTableView.setItems(null);

//                    List<OrderDetailsFx> detailsFxes = ordersService.getOrderDetailsFxObservableList();
//                    detailsFxes.forEach(e->
//                    {
//                        System.out.println("Nazwa produktu" + e.nameProductProperty().getValue());
//                    });

                    updateStockAfterRemoveOrder();
                });
                }
        });


        //ustawienie przycisku pokazania szczegolow
        this.showDetails.setCellFactory(param -> new TableCell<HistoryDataFx, HistoryDataFx>() {
            Button button = createNewButton("/icons/showDetails.jpg");

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
                    ordersService.getOrderDetailsFxObservableList().clear();
                    ordersService.initOrderInformation(item.getId());

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


    public void updateStockAfterRemoveOrder()
    {
        List<OrderDetailsFx> detailsFx = new ArrayList<>();
        List<OrderDetails> details = new ArrayList<>();

        detailsFx.addAll(ordersService.getOrderDetailsFxObservableList());
        detailsFx.forEach(
                e->
                {
                    details.add(ConvertOrderDetails.convertToOrderDetails(e));
                }
        );
        Map<String, Integer> orderDetailsMap = new ConcurrentHashMap<>();
        //po detalach dodaj do mapy, która będzie updatować stock w towarach
        details.forEach(e->
        {
            orderDetailsMap.putIfAbsent(e.getNameProduct(), e.getQty());
        });

        orderDetailsMap.forEach(
                (name,quantity)->
                {
                    System.out.println(name+" "+quantity);
                    ordersService.updateStockAfterRemoveOrder(quantity,name);
                }
        );

    }

}

