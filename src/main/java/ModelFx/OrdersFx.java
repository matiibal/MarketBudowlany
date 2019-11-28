package ModelFx;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrdersFx {

    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> orderDate = new SimpleObjectProperty<>(LocalDate.now());
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private DoubleProperty total = new SimpleDoubleProperty();
    private ObjectProperty<ClientFx> clientFx = new SimpleObjectProperty<>();
    private ArrayList<ObjectProperty<BuiltItemFx>> items = new ArrayList<>();


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public LocalDate getOrderDate() {
        return orderDate.get();
    }

    public ObjectProperty<LocalDate> orderDateProperty() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate.set(orderDate);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public ClientFx getClientFx() {
        return clientFx.get();
    }

    public ObjectProperty<ClientFx> clientFxProperty() {
        return clientFx;
    }

    public void setClientFx(ClientFx clientFx) {
        this.clientFx.set(clientFx);
    }

    public ArrayList<ObjectProperty<BuiltItemFx>> getItems() {
        return items;
    }

    public void setItems(ArrayList<ObjectProperty<BuiltItemFx>> items) {
        this.items = items;
    }
}
