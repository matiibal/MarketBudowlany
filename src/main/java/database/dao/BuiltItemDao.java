package database.dao;

import database.dbutils.DbManager;
import database.models.BuiltItems;


import javax.persistence.Query;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
public class BuiltItemDao extends DbManager implements CommonDaoInterface<BuiltItems,Integer> {

   public BuiltItemDao()
   {

   }


    @Override
    public void persist(BuiltItems entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(BuiltItems entity) {
        getCurrentSession().update(entity);
    }



    public void updateStock(int entityId, int stock)
    {
        String HQL = "update BuiltItems set stock=:stock where id=:id";
        Query query = getCurrentSession().createQuery(HQL);
        query.setParameter("stock",stock);
        query.setParameter("id",entityId );
        query.executeUpdate();
    }

    @Override
    public BuiltItems findById(Integer integer) {
        BuiltItems items = (BuiltItems) getCurrentSession().get(BuiltItems.class, integer);
        return items;
    }

    @Override
    public void delete(BuiltItems entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public List<BuiltItems> findAll() {
        List<BuiltItems> items = (List<BuiltItems>) getCurrentSession().createQuery("from BuiltItems").list();
        return items;
    }

    @Override
    public void deleteAll() {
        List<BuiltItems> entityList = findAll();
        for (BuiltItems entity : entityList) {
            delete(entity);
        }
    }
}
