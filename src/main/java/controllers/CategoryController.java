package controllers;

import ModelFx.CategoryFx;
import ModelFx.CategoryService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import org.hibernate.exception.ConstraintViolationException;
import utils.Dialogs;
import utils.FxmlUtils;


import java.lang.reflect.InvocationTargetException;

import static controllers.ClientController.onlyAlphabet;


public class CategoryController {


    @FXML
    private Button addCategoryButton;
    @FXML
    private Button deleteCategoryButton;
    private CategoryService categoryService;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TableColumn<CategoryFx, String> categoryNameColumn;

    @FXML
    private TableView<CategoryFx> categoryTableView;


    @FXML
    public void initialize() {
        categoryService = new CategoryService();
        this.categoryService.init();

        this.categoryService.categoryFxObjectPropertyProperty().get().nameProperty().bind(this.categoryTextField.textProperty());
        //ustawienie wartości z ol
        this.categoryTableView.setItems(this.categoryService.getCategoryFxObservableList());
        //przypisanie wartości do wierszy
        this.categoryNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        //podpinamy aby móc edytować
        this.categoryNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        FxmlUtils.onlyOneTypeInput(categoryTextField, onlyAlphabet);
        //zapisz do edit nową wartość
        this.categoryTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            this.categoryService.setCategoryFxObjectPropertyEdit(newValue);
        });

        //przyciski włączanie/wyłączanie
        addCategoryButton.disableProperty().bind(categoryTextField.textProperty().isEmpty());
        //deleteCategoryButton.disableProperty().bind(categoryTableView.focusedProperty().not());
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void addCategory() {
        try {
            categoryService.persist();
            categoryService.init();
            Dialogs.dialogConfAddCategory();
        } catch (Exception ex) {
            Dialogs.errorData("category.errorTitle", "category.errorHeader");
        }

        categoryTextField.clear();
    }


    public TableView<CategoryFx> getCategoryTableView() {
        return categoryTableView;
    }

    public void setCategoryTableView(TableView<CategoryFx> categoryTableView) {
        this.categoryTableView = categoryTableView;
    }

    public void editNameCategory(TableColumn.CellEditEvent<CategoryFx, String> categoryFxStringCellEditEvent) {
        //pobieramy wartość
        if (categoryFxStringCellEditEvent.getNewValue().matches(onlyAlphabet)) {
            this.categoryService.getCategoryFxObjectPropertyEdit().setName(categoryFxStringCellEditEvent.getNewValue());

            try {
                this.categoryService.update();
            } catch (Exception ex) {   //jezeli istnieje
                Dialogs.errorData("category.errorTitle", "category.errorHeader");
                this.categoryService.init();
            }
        } else {
            //jezeli nie jest wyrazem
            Dialogs.errorData("client.errorDataTitle", "client.errorDataHeader");
            this.categoryService.init();
        }
    }

    public void deleteCategory() {
        try {
            this.categoryService.delete();
        } catch (Exception ex) {
            Dialogs.errorData("category.errorDeleteTitle", "category.errorDeleteHeader");
        }


    }

}
