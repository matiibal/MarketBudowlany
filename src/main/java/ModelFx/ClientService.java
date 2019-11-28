package ModelFx;

import database.dao.ClientDao;
import database.models.Client;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.converters.ConverterClient;


import java.util.List;

public class ClientService {
    private static ClientDao clientDao;
    private ObjectProperty<ClientFx> clientFxObjectProperty = new SimpleObjectProperty<>(new ClientFx());
    private ObjectProperty<ClientFx> clientFxObjectPropertyEdit = new SimpleObjectProperty<>(new ClientFx());
    private ObservableList<ClientFx> clientFxObservableList = FXCollections.observableArrayList();

    public ClientService() {
        clientDao = new ClientDao();

    }


    public void init()  {
        List<Client> clients = getClients();
        this.clientFxObservableList.clear();
        clients.forEach(e->
        {
           ClientFx clientFx = ConverterClient.convertToClientFx(e);
           this.clientFxObservableList.add(clientFx);
        });

    }

    public List<Client> getClients() {
        clientDao = new ClientDao();
        return this.findAll();
    }






    public void persist() {
        Client client = ConverterClient.convertToClient(this.getClientFxObjectProperty());
        clientDao.openCurrentSessionwithTransaction();
        clientDao.persist(client);
        clientDao.closeCurrentSessionwithTransaction();
    }

    public void update() {
        Client entity=ConverterClient.convertToClient(this.getClientFxObjectPropertyEdit());
        clientDao.openCurrentSessionwithTransaction();
        clientDao.update(entity);
        clientDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public Client findById(Integer id) {
       clientDao.openCurrentSession();
        Client book = clientDao.findById(id);
        clientDao.closeCurrentSession();
        return book;
    }

    public void delete(Integer id) {
        clientDao.openCurrentSessionwithTransaction();
        Client book = clientDao.findById(id);
        clientDao.delete(book);
        clientDao.closeCurrentSessionwithTransaction();
    }

    public List<Client> findAll() {

        clientDao.openCurrentSession();
        List<Client> clients = clientDao.findAll();
        clientDao.closeCurrentSession();
        return clients;
    }

    public void deleteAll() {
        clientDao.openCurrentSessionwithTransaction();
        clientDao.deleteAll();
       clientDao.closeCurrentSessionwithTransaction();
    }

    public ClientDao clientDao() {
        return clientDao;
    }



    public ObjectProperty<ClientFx> clientFxObjectPropertyProperty() {
        return clientFxObjectProperty;
    }
    public ClientFx getClientFxObjectProperty() {
        ModelFx.ClientFx clientFx = clientFxObjectProperty.get();
        return clientFx;
    }

    public ObservableList<ClientFx> getClientFxObservableList() {
        return clientFxObservableList;
    }

    public void setClientFxObservableList(ObservableList<ClientFx> clientFxObservableList) {
        this.clientFxObservableList = clientFxObservableList;
    }

    public ClientFx getClientFxObjectPropertyEdit() {
        return clientFxObjectPropertyEdit.get();
    }

    public ObjectProperty<ClientFx> clientFxObjectPropertyEditProperty() {
        return clientFxObjectPropertyEdit;
    }
    public void setClientFxObjectPropertyEdit(ClientFx clientFxObjectPropertyEdit) {
        this.clientFxObjectPropertyEdit.set(clientFxObjectPropertyEdit);
    }
}
