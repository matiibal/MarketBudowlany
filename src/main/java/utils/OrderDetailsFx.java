package utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderDetailsFx {

    StringProperty quantity = new SimpleStringProperty();
    StringProperty nameProduct = new SimpleStringProperty();


    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getNameProduct() {
        return nameProduct.get();
    }

    public StringProperty nameProductProperty() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct.set(nameProduct);
    }
}
