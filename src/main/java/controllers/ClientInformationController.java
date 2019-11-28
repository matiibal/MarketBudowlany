package controllers;

import ModelFx.ClientFx;
import ModelFx.ClientService;

import javafx.beans.binding.StringBinding;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import utils.Dialogs;

import static javafx.beans.binding.Bindings.createStringBinding;


@SuppressWarnings("ALL")
public class ClientInformationController {

    private static final String onlyAlphabet = "^[a-zA-Z\\s\\p{IsLatin}]*$";
    private static final String onlyNumberNip = "\\d{10}?";
    private static final String onlyNumberPhone = "\\d{9}?";
    private static final String postRedix = "\\d{2}-\\d{3}?";
    private static final String onlyHouseNumber = "\\d{1,4}[a-zA-Z\\s\\p{IsLatin}]*?";

    @FXML
    private TableColumn<ClientFx, String> nameColumn;
    @FXML
    private TableColumn<ClientFx, String> secondNameColumn;
    @FXML
    private TableColumn<ClientFx, String> nipColumn;
    @FXML
    private TableColumn<ClientFx, String> streetColumn;
    @FXML
    private TableColumn<ClientFx, String> houseNrColumn;
    @FXML
    private TableColumn<ClientFx, String> postColumn;
    @FXML
    private TableColumn<ClientFx, String> cityColumn;
    @FXML
    private TableColumn<ClientFx, String> phoneColumn;
    @FXML
    private TableView<ClientFx> clientTableView;
    @FXML
    private TextField filterField;
    private ClientService clientService;
    private StringBinding stringBinding;

    @FXML
    public void initialize() {
        clientService = new ClientService();

        //załadowanie danymi tabeli
        this.clientService.init();
        this.clientTableView.setItems(this.clientService.getClientFxObservableList());
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.secondNameColumn.setCellValueFactory(cellData -> cellData.getValue().secondNameProperty());
        this.nipColumn.setCellValueFactory(cellData -> cellData.getValue().NIPProperty());
        this.streetColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
        this.houseNrColumn.setCellValueFactory(cellData -> cellData.getValue().houseNumberProperty());
        this.cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        this.phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        //wczytanie z dwóch tabel
        this.postColumn.setCellValueFactory(cellData -> (createStringBinding(
                () -> (cellData.getValue().postcodeProperty1()).getValue() + " - " + (cellData.getValue().postcodeProperty2()).getValue()
        )));

        //podpinamy aby móc edytować
        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.secondNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.nipColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.streetColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.houseNrColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.cityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.postColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //zapisz do edit nową wartość
        this.clientTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            this.clientService.setClientFxObjectPropertyEdit(newValue);
        });


        searchClient();


    }

    public void searchClient() {
        FilteredList<ClientFx> filteredData = new FilteredList<>(this.clientService.getClientFxObservableList(), b->true);
        this.filterField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            filteredData.setPredicate(clientFx ->
            {
             if(newValue == null || newValue.isEmpty())
             {
                 return true;
             }
             String lowerCaseFilter = newValue.toLowerCase();
             if(clientFx.getSecondName().toLowerCase().contains(lowerCaseFilter))
             {
                 return true;
             }
             else if(clientFx.getNIP().toLowerCase().contains(lowerCaseFilter))
             {
                 return true;
             }
            else
                return false;
            }

            );
        });

        SortedList<ClientFx> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clientTableView.comparatorProperty());
        clientTableView.setItems(sortedData);
    }


    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void nameEditCommit(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        //pobieramy wartość
        if (clientFxStringCellEditEvent.getNewValue().matches(onlyAlphabet)) {
            this.clientService.getClientFxObjectPropertyEdit().setName(clientFxStringCellEditEvent.getNewValue());
            this.clientService.update();
        } else {
            Dialogs.errorData("client.errorDataTitle","client.errorDataHeader");
            this.clientService.init();
        }

    }

    public void secondNameEditCommit(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        //pobieramy wartość
        if (clientFxStringCellEditEvent.getNewValue().matches(onlyAlphabet)) {
            this.clientService.getClientFxObjectPropertyEdit().setSecondName(clientFxStringCellEditEvent.getNewValue());
            this.clientService.update();
        } else {
            Dialogs.errorData("client.errorDataTitle","client.errorDataHeader");
            this.clientService.init();
        }
    }

    public void nipEditCommit(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        //pobieramy wartość
        if (clientFxStringCellEditEvent.getNewValue().matches(onlyNumberNip)) {
            this.clientService.getClientFxObjectPropertyEdit().setNIP(clientFxStringCellEditEvent.getNewValue());

           try {
               this.clientService.update();
           }
           catch (Exception ex)
           {
               //taki sam NIP
               Dialogs.errorData("client.errorTitle","client.errorHeader");
               this.clientService.init();
           }

        } else {
            //jezeli nie pasuje do wzorca
            Dialogs.errorData("client.errorDataTitle","client.errorDataHeader");
            this.clientService.init();
        }
    }

    public void streetEditCommit(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        //pobieramy wartość
        if (clientFxStringCellEditEvent.getNewValue().matches(onlyAlphabet)) {
            this.clientService.getClientFxObjectPropertyEdit().setStreet(clientFxStringCellEditEvent.getNewValue());
            this.clientService.update();
        } else {
            Dialogs.errorData("client.errorDataTitle","client.errorDataHeader");
            this.clientService.init();
        }
    }

    public void houseNrEditCommit(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        //pobieramy wartość
        if (clientFxStringCellEditEvent.getNewValue().matches(onlyHouseNumber)) {
            this.clientService.getClientFxObjectPropertyEdit().setHouseNumber(clientFxStringCellEditEvent.getNewValue());
            this.clientService.update();
        } else {
            Dialogs.errorData("client.errorDataTitle","client.errorDataHeader");
            this.clientService.init();
        }
    }


    public void postEditCommit(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        //czy pasuje do wzorca
        if (clientFxStringCellEditEvent.getNewValue().matches(postRedix)) {
            //pobieranie podciągów
            this.clientService.getClientFxObjectPropertyEdit().setPostcode1(clientFxStringCellEditEvent.getNewValue().substring(0, 2));
            this.clientService.getClientFxObjectPropertyEdit().setPostcode2(clientFxStringCellEditEvent.getNewValue().substring(3, 6));
            this.clientService.update();

        } else {
            Dialogs.errorData("client.errorDataTitle","client.errorDataHeader");
            this.clientService.init();
        }
    }

    public void cityEditCommit(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        //pobieramy wartość
        if (clientFxStringCellEditEvent.getNewValue().matches(onlyAlphabet)) {
            this.clientService.getClientFxObjectPropertyEdit().setCity(clientFxStringCellEditEvent.getNewValue());
            this.clientService.update();
        } else {
            Dialogs.errorData("client.errorDataTitle","client.errorDataHeader");
            this.clientService.init();
        }
    }

    public void phoneEditCommit(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        //pobieramy wartość
        if (clientFxStringCellEditEvent.getNewValue().matches(onlyNumberPhone)) {
            this.clientService.getClientFxObjectPropertyEdit().setPhoneNumber(clientFxStringCellEditEvent.getNewValue());
            this.clientService.update();
        } else {
            Dialogs.errorData("client.errorDataTitle","client.errorDataHeader");
            this.clientService.init();
        }
    }

    public TextField getFilterField() {
        return filterField;
    }

    public void setFilterField(TextField filterField) {
        this.filterField = filterField;
    }
}
