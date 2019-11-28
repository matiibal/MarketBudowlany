package utils.converters;

import ModelFx.ClientFx;
import database.models.Client;

public class ConverterClient {
    @SuppressWarnings("Duplicates")
    public static Client convertToClient(ClientFx clientFx)
    {
        Client client = new Client();
        client.setId(clientFx.getId());
        client.setName(clientFx.getName());
        client.setSecondName(clientFx.getSecondName());
        client.setStreet(clientFx.getStreet());
        client.setNIP(clientFx.getNIP());
        client.setPostcode1(clientFx.getPostcode1());
        client.setPostcode2(clientFx.getPostcode2());
        client.setHouseNumber(clientFx.getHouseNumber());
        client.setCity(clientFx.getCity());
        client.setPhoneNumber(clientFx.getPhoneNumber());
        return client;
    }
    @SuppressWarnings("Duplicates")
    public static ClientFx convertToClientFx(Client client)
    {
        ClientFx clientFx = new ClientFx();
        clientFx.setId(client.getId());
        clientFx.setName(client.getName());
        clientFx.setSecondName(client.getSecondName());
        clientFx.setStreet(client.getStreet());
        clientFx.setNIP(client.getNIP());
        clientFx.setPostcode1(client.getPostcode1());
        clientFx.setPostcode2(client.getPostcode2());
        clientFx.setHouseNumber(client.getHouseNumber());
        clientFx.setCity(client.getCity());
        clientFx.setPhoneNumber(client.getPhoneNumber());
        return clientFx;
    }
}
