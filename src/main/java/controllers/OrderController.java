package controllers;

import ModelFx.*;
import database.models.BuiltItems;
import database.models.Orders;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.Dialogs;
import utils.converters.ConverterBuiltItems;
import utils.converters.ConverterClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static utils.FxmlUtils.onlyOneTypeInput;


@SuppressWarnings("ALL")
public class OrderController {

    public TableColumn<ClientFx, String> nameClientColumn;
    public TableColumn<ClientFx, String> secondNameClientColumn;
    public TableColumn<ClientFx, String> nipColumn;
    public Label nameItemLabel;
    public Label priceItemLabel;
    public TextField quantityTextField;
    public Button addButton;
    public TextField searchItemField;
    public TableView<BuiltItemFx> itemsTableView;
    public TableColumn<BuiltItemFx, String> nameItemsColumn;
    public TableColumn<BuiltItemFx, String> categoryColumn;
    public TableColumn<BuiltItemFx, String> priceColumn;
    public TableColumn<BuiltItemFx, BuiltItemFx> addColumn;
    public Label clientLabel;
    public Label dateLabel;
    public Label priceAllLabel;
    public TableView<BuiltItemFx> orderTableView;
    public TableColumn<BuiltItemFx, String> orderNameItem;
    public TableColumn<BuiltItemFx, String> orderItemQuantity;
    public TableColumn<BuiltItemFx, String> priceByOneItem;
    public TableColumn<BuiltItemFx, String> allPriceColumn;
    public TableColumn<BuiltItemFx, BuiltItemFx> deleteColumn;
    private Map<BuiltItems, Integer> dataOrder = new ConcurrentHashMap<>();
    @FXML
    private TableView<ClientFx> clientTableView;
    @FXML
    private TextField searchClientField;
    private ClientService clientService;
    private BuiltItemService builtItemService;
    private OrdersService ordersService;
    private ObservableList<BuiltItemFx> orderList = FXCollections.observableArrayList();
    private Orders order = new Orders();
    private ArrayList<String> orderRow = new ArrayList<>();
    private ArrayList<String> itemRow = new ArrayList<>();
    @FXML
    private Button commitOrder;

    @FXML
    public void initialize() {
        clientService = new ClientService();
        builtItemService = new BuiltItemService();
        ordersService = new OrdersService();
        //załadowanie danymi tabeli

        onlyOneTypeInput(quantityTextField, "\\d{0,9}?");
        clientTableView();
        setLabels();
        builtItemTableView();
        BuiltItemController controller = new BuiltItemController();
        addItemToOrder();
        deleteItemsFromOrder(controller);
        searchClient();
        searchItem();






    }

    public void deleteItemsFromOrder(BuiltItemController controller) {
        this.deleteColumn.setCellFactory(param -> new TableCell<BuiltItemFx, BuiltItemFx>() {
            Button button = controller.createNewButton("/icons/delete.jpg");

            //tworzenie komórki
            protected void updateItem(BuiltItemFx item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);

                button.setOnAction(
                        e ->
                        {
                            orderList.remove(item);
                            orderTableView.setItems(ordersService.getOrderItemList());
                            orderNameItem.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
                            priceByOneItem.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
                            Double price = Double.valueOf(item.priceProperty().getValue());
                            int quantity = Integer.parseInt(orderItemQuantity.getCellObservableValue(item).getValue());
                            priceAllLabel.setText(String.valueOf(order.totalAfterRemoving(price, quantity)).concat(" PLN")); //zliczanie);

                            ordersService.setOrderItemList(orderList);

                            //usuwanie z mapy
                            dataOrder.keySet().forEach(
                                    lf ->
                                    {
                                        if (lf.getName().equals(item.getName())) {
                                            dataOrder.remove(lf);
                                        }
                                    }
                            );
                            order.setItem(dataOrder);

                            orderRow.remove(item.getName());

                        }
                );
            }


        });
    }

    public BuiltItemController addItemToOrder() {
        BuiltItemController controller = new BuiltItemController();
        this.addColumn.setCellFactory(param -> new TableCell<BuiltItemFx, BuiltItemFx>() {
            Button button = controller.createNewButton("/icons/add.jpg");

            //tworzenie komórki
            protected void updateItem(BuiltItemFx item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.disableProperty().bind(quantityTextField.textProperty().isEmpty());
                button.setOnAction(
                        e ->
                        {

                            if (orderRow.contains(item.getName())) {
                                Dialogs.errorData("order.error", "order.header");
                            } else {
                                orderRow.add(item.getName());
                                orderList.add(item);

                                ordersService.setOrderItemList(orderList);
                                orderTableView.setItems(ordersService.getOrderItemList());
                                orderNameItem.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
                                priceByOneItem.setCellValueFactory(cellData -> cellData.getValue().priceProperty());


                                String quantity = quantityTextField.getText();
                                item.setQuantity(quantity);
                                double priceItem = Double.parseDouble(item.getPrice());
                                double columnPrice = Double.parseDouble(quantity) * priceItem;
                                item.setTotalPrice(String.valueOf(columnPrice));

                                orderItemQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
                                allPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

                                //mapowanie do bazy danych
                                dataOrder.putIfAbsent(ConverterBuiltItems.convertToBuiltItems(item), Integer.valueOf(quantity));
                                order.setItem(dataOrder);


                                Double price = Double.valueOf(item.priceProperty().getValue());
                                priceAllLabel.setText(String.valueOf(Math.round(order.total(price, Integer.parseInt(quantity)) * 100.00) / 100.00).concat(" PLN")); //zliczanie);
                                quantityTextField.clear();


                            }

                        }

                );


            }


        });
        return controller;
    }

    public void builtItemTableView() {
        this.builtItemService.init();
        this.itemsTableView.setItems(this.builtItemService.getBuiltItemFxObservableList());
        this.nameItemsColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryFxProperty().get().nameProperty());
        this.priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

        this.addColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        this.deleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
    }

    public void clientTableView() {
        this.clientService.init();
        this.clientTableView.setItems(this.clientService.getClientFxObservableList());
        this.nameClientColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.secondNameClientColumn.setCellValueFactory(cellData -> cellData.getValue().secondNameProperty());
        this.nipColumn.setCellValueFactory(cellData -> cellData.getValue().NIPProperty());
    }

    public void setLabels() {
        //zaznaczenie klienta spowoduje dodanie go do zamówienia
        this.clientTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            clientLabel.setText(newValue.getName() + " " + newValue.getSecondName());
            order.setClient(ConverterClient.convertToClient(newValue));
        });


        //zaznaczenie klienta spowoduje dodanie go do zamówienia
        this.itemsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            nameItemLabel.setText(newValue.getName());
            priceItemLabel.setText(newValue.getPrice());

        });

        this.dateLabel.textProperty().set(order.getOrderDate().now().toString());
    }


    public void searchClient() {
        FilteredList<ClientFx> filteredData = new FilteredList<>(this.clientService.getClientFxObservableList(), b -> true);
        this.searchClientField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            filteredData.setPredicate(clientFx ->
                    {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (clientFx.getSecondName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (clientFx.getNIP().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else
                            return false;
                    }

            );
        });

        SortedList<ClientFx> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clientTableView.comparatorProperty());
        clientTableView.setItems(sortedData);
    }

    public void searchItem() {
        FilteredList<BuiltItemFx> filteredData = new FilteredList<>(this.builtItemService.getBuiltItemFxObservableList(), b -> true);
        this.searchItemField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            filteredData.setPredicate(builtItemFx ->
                    {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (builtItemFx.getName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (builtItemFx.getCategoryFx().getName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else
                            return false;
                    }

            );
        });
        SortedList<BuiltItemFx> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(itemsTableView.comparatorProperty());
        itemsTableView.setItems(sortedData);
    }


    public void comitOrder() {

        order.setOrderDate(LocalDate.parse(order.getOrderDate().now().toString()));
        OrdersService service = new OrdersService();
        try {
            if(order.getClient().equals(null) || order.getItem().isEmpty()) throw new Exception();
            ordersService.persist(order);
            Dialogs.dialogConfAddOrder();
            clean();

        }catch (Exception ex)
        {
            Dialogs.errorData("order.errorClient","order.errorClient.header");
        }




    }

    public void clean() {
        orderTableView.getItems().clear();
        dataOrder.clear();
        orderRow.clear();
        order.setClient(null);
        priceAllLabel.setText("0 PLN");
        clientLabel.setText("Nie wybrano");
        quantityTextField.clear();
    }

    public void cancelOrder() {
    clean();
    }
}
