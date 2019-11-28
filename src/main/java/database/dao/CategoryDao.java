package database.dao;

import database.dbutils.DbManager;
import database.models.Category;
import database.models.Client;

import java.util.List;

@SuppressWarnings("ALL")
public class CategoryDao extends DbManager implements CommonDaoInterface<Category, Integer> {


    public CategoryDao()
    {

    }

    @Override
    public void persist(Category entity) {
        getCurrentSession().save(entity);
    }

    public void update(Category entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Category findById(Integer id) {
        Category category = (Category) getCurrentSession().get(Category.class, id);
        return category;
    }

    @Override
    public void delete(Category entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAll() {
        List<Category> categories = (List<Category>) getCurrentSession().createQuery("from Category").list();
        return categories;
    }

    public void deleteAll() {
        List<Category> entityList = findAll();
        for (Category entity : entityList) {
            delete(entity);
        }
    }
}
