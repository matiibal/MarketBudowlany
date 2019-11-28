package ModelFx;

import database.dao.CategoryDao;
import database.models.Category;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.converters.ConverterCategory;
import java.util.List;


public class CategoryService {
    private CategoryDao categoryDao;
    private ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>(new CategoryFx());
    private ObjectProperty<CategoryFx> categoryFxObjectPropertyEdit = new SimpleObjectProperty<>(new CategoryFx());
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();

    public CategoryService()
    {
        categoryDao = new CategoryDao();
    }

    public void init()  {
        List<Category> categories = getCategories();

        this.categoryFxObservableList.clear();

        categories.forEach(e->
        {

            CategoryFx categoryFx = ConverterCategory.convertToCategoryFx(e);
            this.categoryFxObservableList.add(categoryFx);
        });

    }

    public List<Category> getCategories() {
        categoryDao = new CategoryDao();
        return this.findAll();
    }

    public void persist() {

        Category category = ConverterCategory.convertToCategory(this.getCategoryFxObjectProperty());
        categoryDao.openCurrentSessionwithTransaction();
        categoryDao.persist(category);
        categoryDao.closeCurrentSessionwithTransaction();
    }

    public void update() {
       Category entity=ConverterCategory.convertToCategory(this.getCategoryFxObjectPropertyEdit());
        categoryDao.openCurrentSessionwithTransaction();
        categoryDao.update(entity);
        categoryDao.closeCurrentSessionwithTransaction();
       this.init();
    }

    public void delete() {
        categoryDao.openCurrentSessionwithTransaction();
        Integer id = this.getCategoryFxObjectPropertyEdit().getId();
        Category category = categoryDao.findById(id);
        categoryDao.delete(category);
        categoryDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public void delete(Integer id) {
        categoryDao.openCurrentSessionwithTransaction();
        Category category = categoryDao.findById(id);
        categoryDao.delete(category);
        categoryDao.closeCurrentSessionwithTransaction();
    }

    public List<Category> findAll() {

        categoryDao.openCurrentSession();
        List<Category> clients = categoryDao.findAll();
        categoryDao.closeCurrentSession();
        return clients;
    }
    public Category findById(Integer id)  {
        categoryDao.openCurrentSession();
        Category category = categoryDao.findById(id);
        categoryDao.closeCurrentSession();
        return category;
    }


    public void deleteAll() {
        categoryDao.openCurrentSessionwithTransaction();
        categoryDao.deleteAll();
        categoryDao.closeCurrentSessionwithTransaction();
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public CategoryFx getCategoryFxObjectProperty() {
        return categoryFxObjectProperty.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyProperty() {
        return categoryFxObjectProperty;
    }

    public void setCategoryFxObjectProperty(CategoryFx categoryFxObjectProperty) {
        this.categoryFxObjectProperty.set(categoryFxObjectProperty);
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public CategoryFx getCategoryFxObjectPropertyEdit() {
        return categoryFxObjectPropertyEdit.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyEditProperty() {
        return categoryFxObjectPropertyEdit;
    }

    public void setCategoryFxObjectPropertyEdit(CategoryFx categoryFxObjectPropertyEdit) {
        this.categoryFxObjectPropertyEdit.set(categoryFxObjectPropertyEdit);
    }
}
