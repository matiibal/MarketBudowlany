package database.dao;

import database.dbutils.DbManager;
import database.models.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@SuppressWarnings("ALL")
public class ClientDao extends DbManager implements CommonDaoInterface<Client, Integer> {


    public ClientDao()
    {

    }

    public void persist(Client entity) {
        getCurrentSession().save(entity);
    }

    public void update(Client entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Client findById(Integer id) {
        Client client = (Client) getCurrentSession().get(Client.class, id);
        return client;
    }

    public void delete(Client entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Client> findAll() {
        List<Client> clients = (List<Client>) getCurrentSession().createQuery("from Client ").list();
        return clients;
    }

    public void deleteAll() {
        List<Client> entityList = findAll();
        for (Client entity : entityList) {
            delete(entity);
        }
    }
}
