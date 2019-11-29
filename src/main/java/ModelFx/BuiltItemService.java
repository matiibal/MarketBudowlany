package ModelFx;

import database.dao.BuiltItemDao;
import database.dao.CategoryDao;
import database.models.BuiltItems;
import database.models.Category;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import utils.converters.ConverterBuiltItems;
import utils.converters.ConverterCategory;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public class BuiltItemService {
    private BuiltItemDao builtItemDao;
    private ObjectProperty<BuiltItemFx> builtItemFxObjectProperty = new SimpleObjectProperty<>(new BuiltItemFx());
    private ObjectProperty<BuiltItemFx> builtItemFxObjectPropertyEdit = new SimpleObjectProperty<>(new BuiltItemFx());

    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();
    private ObservableList<BuiltItemFx> builtItemFxObservableList = FXCollections.observableArrayList();
    
    public BuiltItemService()
    {
        builtItemDao = new BuiltItemDao();
    }


    public void init()  {

        //dodanie do observable list
        List<BuiltItems> items = getItems();

        this.builtItemFxObservableList.clear();

        items.forEach(e->
        {
            BuiltItemFx builtItemFx = ConverterBuiltItems.convertToBuiltItemsFx(e);
            this.builtItemFxObservableList.add(builtItemFx);

        });

    }

    public List<BuiltItems> getItems() {
        builtItemDao = new BuiltItemDao();
        return this.findAll();
    }







    public void persist() {
        BuiltItems items = ConverterBuiltItems.convertToBuiltItems(this.getBuiltItemFxObjectProperty());

        builtItemDao.openCurrentSessionwithTransaction();
        builtItemDao.persist(items);
        builtItemDao.closeCurrentSessionwithTransaction();
    }


    public void persist(BuiltItems items) {

        builtItemDao.openCurrentSessionwithTransaction();
        builtItemDao.persist(items);
        builtItemDao.closeCurrentSessionwithTransaction();
    }
    public void update() {
        BuiltItems entity=ConverterBuiltItems.convertToBuiltItems(this.getBuiltItemFxObjectPropertyEdit());
        builtItemDao.openCurrentSessionwithTransaction();
        builtItemDao.update(entity);
        builtItemDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public void update(BuiltItems entity) {
        builtItemDao.openCurrentSessionwithTransaction();
        builtItemDao.update(entity);
        builtItemDao.closeCurrentSessionwithTransaction();
        this.init();
    }


    public void updateStock(int entityId, int stock) {
        builtItemDao.openCurrentSessionwithTransaction();
        builtItemDao.updateStock(entityId,stock);
        builtItemDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public void delete() {
        builtItemDao.openCurrentSessionwithTransaction();
        Integer id = this.getBuiltItemFxObjectPropertyEdit().getId();
        BuiltItems builtItems = builtItemDao.findById(id);
        builtItemDao.delete(builtItems);
        builtItemDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public void delete(Integer id) {
        builtItemDao.openCurrentSessionwithTransaction();
        BuiltItems builtItems = builtItemDao.findById(id);
        builtItemDao.delete(builtItems);
        builtItemDao.closeCurrentSessionwithTransaction();
    }

    public List<BuiltItems> findAll() {

        builtItemDao.openCurrentSession();
        List<BuiltItems> clients = builtItemDao.findAll();
        builtItemDao.closeCurrentSession();
        return clients;
    }

    public void deleteAll() {
        builtItemDao.openCurrentSessionwithTransaction();
        builtItemDao.deleteAll();
        builtItemDao.closeCurrentSessionwithTransaction();
    }

    public void initCategoryList(){
        CategoryService categoryService = new CategoryService();
        List<Category> categoryList = categoryService.getCategories();

        categoryFxObservableList.clear();
        categoryList.forEach(e->
        {
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFx(e);
            categoryFxObservableList.add(categoryFx);
        });
    }

    public BuiltItems findById(Integer id)  {
        builtItemDao.openCurrentSession();
        BuiltItems builtItems = builtItemDao.findById(id);
        builtItemDao.closeCurrentSession();
        return builtItems;
    }


    public BuiltItemFx getBuiltItemFxObjectProperty() {
        return builtItemFxObjectProperty.get();
    }

    public ObjectProperty<BuiltItemFx> builtItemFxObjectPropertyProperty() {
        return builtItemFxObjectProperty;
    }

    public void setBuiltItemFxObjectProperty(BuiltItemFx builtItemFxObjectProperty) {
        this.builtItemFxObjectProperty.set(builtItemFxObjectProperty);
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public ObservableList<BuiltItemFx> getBuiltItemFxObservableList() {
        return builtItemFxObservableList;
    }

    public void setBuiltItemFxObservableList(ObservableList<BuiltItemFx> builtItemFxObservableList) {
        this.builtItemFxObservableList = builtItemFxObservableList;
    }

    public BuiltItemFx getBuiltItemFxObjectPropertyEdit() {
        return builtItemFxObjectPropertyEdit.get();
    }

    public ObjectProperty<BuiltItemFx> builtItemFxObjectPropertyEditProperty() {
        return builtItemFxObjectPropertyEdit;
    }

    public void setBuiltItemFxObjectPropertyEdit(BuiltItemFx builtItemFxObjectPropertyEdit) {
        this.builtItemFxObjectPropertyEdit.set(builtItemFxObjectPropertyEdit);
    }

    public void delete(BuiltItemFx item) {
        builtItemDao.openCurrentSessionwithTransaction();
        BuiltItems builtItems = builtItemDao.findById(item.getId());
        builtItemDao.delete(builtItems);
        builtItemDao.closeCurrentSessionwithTransaction();
        this.init();

    }
}
