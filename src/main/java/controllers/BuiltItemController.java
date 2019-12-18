package controllers;

import ModelFx.BuiltItemFx;
import ModelFx.BuiltItemService;
import ModelFx.CategoryFx;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Dialogs;
import utils.FxmlUtils;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;


@SuppressWarnings("Duplicates")
public class BuiltItemController {
    @FXML
    private TextField itemNameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;

    @FXML
    private TextField searchItemsField;

    @FXML
    private TableColumn<BuiltItemFx, String> nameItemsColumn;
    @FXML
    private TableColumn<BuiltItemFx, String> categoryItemsColumn;
    @FXML
    private TableColumn<BuiltItemFx, String> priceItemsColumn;
    @FXML
    private TableColumn<BuiltItemFx, String> stockItemsColumn;
    @FXML
    private TableColumn<BuiltItemFx, BuiltItemFx> deleteColumn;
    @FXML
    private TableColumn<BuiltItemFx, BuiltItemFx> editColumn;

    @FXML
    private TableView<BuiltItemFx> itemsTableView;


    private BuiltItemService builtItemService;
    //wyrażenia regularne
    private static final String onlyNumeric = "\\d{0,8}?";
    private static final String onlyDouble = "\\d{0,8}\\.{0,1}\\d{0,2}?";


    public Button createNewButton(String source) {
        Button buttonAddItemToOrder = new Button();
        javafx.scene.image.Image image = new Image(this.getClass().getResource(source).toString());
        ImageView imageView = new ImageView(image);
        buttonAddItemToOrder.setGraphic(imageView);
        return buttonAddItemToOrder;
    }

    @SuppressWarnings("Duplicates")
    @FXML

    public void initialize() {
        builtItemService = new BuiltItemService();

        this.builtItemService.init();

        bindings();
        this.categoryComboBox.setItems(this.builtItemService.getCategoryFxObservableList());
        //wczytanie listy kategorii
        this.builtItemService.initCategoryList();


        //zapis do tabeli
        this.itemsTableView.setItems(this.builtItemService.getBuiltItemFxObservableList());
        //przypisanie wartości do wierszy
        this.nameItemsColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.stockItemsColumn.setCellValueFactory(cellData -> cellData.getValue().stockProperty());
        this.priceItemsColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        this.deleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        this.editColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        //ustawienie przycisku usuwania
        this.deleteColumn.setCellFactory(param -> new TableCell<BuiltItemFx, BuiltItemFx>() {
            Button button = createNewButton("/icons/delete.jpg");

            //tworzenie komórki
            protected void updateItem(BuiltItemFx item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event ->
                {
                    try {
                        builtItemService.delete(item);
                    }
                    catch (Exception ex)
                    {
                        Dialogs.errorData("items.errorDeleteTitle",
                                "items.errorDeleteHeader");
                    }
                });
            }
        });


        //ustawienie przycisku edycji
        this.editColumn.setCellFactory(param -> new TableCell<BuiltItemFx, BuiltItemFx>() {
            Button button = createNewButton("/icons/edit.jpg");

            //tworzenie komórki
            protected void updateItem(BuiltItemFx item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event ->
                {
                    builtItemService.init();

                    FXMLLoader loader = FxmlUtils.getLoader("/fxml/AddItem.fxml");
                    Scene scene = null;
                    try {
                        scene = new Scene(loader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    EditController controller = loader.getController();
                    controller.getBuiltItemService().setBuiltItemFxObjectProperty(item);
                    controller.bindings();

                    Stage stage = new Stage();
                    stage.setScene(scene);

                    //okno modalne nad aplikacją
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                    builtItemService.init();

                });
            }
        });


        //wyswietlanie kategorii
        this.categoryItemsColumn.setCellValueFactory(cellData -> cellData.getValue().categoryFxProperty().get().nameProperty());
        searchItems();

    }

    public void bindings() {
        this.itemNameTextField.textProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().nameProperty());
        //przypisanie do properties obiektu z combo box
        this.categoryComboBox.valueProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().categoryFxProperty());
       // FxmlUtils.onlyOneTypeInput(priceTextField, onlyDouble);
        this.priceTextField.textProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().priceProperty());
       // FxmlUtils.onlyOneTypeInput(stockTextField, onlyNumeric);
        this.stockTextField.textProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().stockProperty());
        this.categoryComboBox.valueProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().categoryFxProperty());
        FxmlUtils.onlyOneTypeInput(priceTextField, onlyDouble);
        FxmlUtils.onlyOneTypeInput(stockTextField, onlyNumeric);


    }
    public void searchItems() {
        FilteredList<BuiltItemFx> filteredData = new FilteredList<>(this.builtItemService.getBuiltItemFxObservableList(), b->true);
        this.searchItemsField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            filteredData.setPredicate(builtItemFx ->
                    {
                        if(newValue == null || newValue.isEmpty())
                        {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if(builtItemFx.getName().toLowerCase().contains(lowerCaseFilter))
                        {
                            return true;
                        }
                        else if(builtItemFx.getCategoryFx().getName().toLowerCase().contains(lowerCaseFilter))
                        {
                            return true;
                        }
                        else
                            return false;
                    }

            );
        });

        SortedList<BuiltItemFx> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(itemsTableView.comparatorProperty());
        itemsTableView.setItems(sortedData);
    }


    public void addItems() {

        builtItemService.persist();
        builtItemService.init();
        Dialogs.dialogConfAddItems();
        builtItemService.init();
        itemNameTextField.clear();
        priceTextField.clear();
        stockTextField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
    }

}
