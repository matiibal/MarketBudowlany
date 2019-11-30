package database.dao;

import database.dbutils.DbManager;

import database.models.Orders;
import javafx.beans.property.ListProperty;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import utils.BarchartData;
import utils.HistoryData;
import utils.PieData;

import java.util.List;

@SuppressWarnings({"JpaQlInspection", "SqlResolve"})
public class OrdersDao extends DbManager implements CommonDaoInterface<Orders, Integer> {

    public OrdersDao() {

    }


    @Override
    public void persist(Orders entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Orders entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Orders findById(Integer integer) {
        Orders order = (Orders) getCurrentSession().get(Orders.class, integer);
        return order;
    }

    @Override
    public void delete(Orders entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Orders> findAll() {
        List<Orders> orders = (List<Orders>) getCurrentSession().createQuery("from Orders").list();
        return orders;
    }


    public List<PieData> findOrderSale() {

        String hql = "select name, sum(qty) as quantity from order_details join builtItems on order_details.item_id=builtItems.id  group by item_id order by quantity desc limit 10";

        Query query = getCurrentSession().createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(PieData.class));
        List<PieData> data = query.list();
        return data;
    }

    public List<BarchartData> findBestClient() {

        String hql = "select concat(client.name,' ', client.secondName) as clientData, sum(totalPrice) as sum from orders join client on orders.client_id=client.id group by client.id order by sum desc limit 5";

        Query query = getCurrentSession().createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(BarchartData.class));
        List<BarchartData> data = query.list();
        return data;
    }

    public List<HistoryData> findHistoryData()
    {
        String sql = "select secondName, nip, totalPrice, orderDate from orders join client on orders.client_id=client.id order by orderDate desc";
        Query query = getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(HistoryData.class));
        List<HistoryData> data = query.list();
        return data;
    }




    @Override
    public void deleteAll() {
        List<Orders> entityList = findAll();
        for (Orders entity : entityList) {
            delete(entity);
        }
    }
}
