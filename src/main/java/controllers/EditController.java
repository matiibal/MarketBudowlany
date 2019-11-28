package controllers;

import ModelFx.BuiltItemService;
import ModelFx.CategoryFx;
import database.dao.BuiltItemDao;
import database.models.BuiltItems;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import utils.Dialogs;
import utils.FxmlUtils;
import utils.converters.ConverterBuiltItems;

@SuppressWarnings("Duplicates")
public class EditController {

    @FXML
    private TextField itemNameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    private static final String onlyNumeric = "\\d{0,8}?";
    private static final String onlyDouble = "\\d{0,8}\\.{0,1}\\d{0,2}?";
    public BuiltItemService getBuiltItemService() {
        return builtItemService;
    }

    private BuiltItemService builtItemService;


    @FXML
    public void initialize() {
        builtItemService = new BuiltItemService();

        this.builtItemService.init();

        bindings();
        this.categoryComboBox.setItems(this.builtItemService.getCategoryFxObservableList());
        //wczytanie listy kategorii
        this.builtItemService.initCategoryList();


    }

    public void bindings() {
        this.itemNameTextField.textProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().nameProperty());
        //przypisanie do properties obiektu z combo box
        this.categoryComboBox.valueProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().categoryFxProperty());
        this.priceTextField.textProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().priceProperty());
        this.stockTextField.textProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().stockProperty());
        this.categoryComboBox.valueProperty().bindBidirectional(this.builtItemService.builtItemFxObjectPropertyProperty().get().categoryFxProperty());
        FxmlUtils.onlyOneTypeInput(priceTextField, onlyDouble);
       FxmlUtils.onlyOneTypeInput(stockTextField, onlyNumeric);

    }


    @FXML
    public void updateItem() {


        try {
            BuiltItemDao builtItemDao = new BuiltItemDao();
            BuiltItems entity = ConverterBuiltItems.convertToBuiltItems(builtItemService.getBuiltItemFxObjectProperty());
            builtItemDao.openCurrentSessionwithTransaction();
            builtItemDao.update(entity);
            builtItemDao.closeCurrentSessionwithTransaction();
            builtItemService.init();
            Dialogs.dialogConfirmationEditItems();


        } catch (Exception ex) {
            Dialogs.errorData("itemsEdit.error.title", "itemsEdit.error.header");
        }


    }

}
