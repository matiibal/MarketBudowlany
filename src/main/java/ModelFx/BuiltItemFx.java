package ModelFx;

import javafx.beans.property.*;

public class BuiltItemFx {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty price = new SimpleStringProperty();
    private StringProperty stock = new SimpleStringProperty();
    private ObjectProperty<CategoryFx> categoryFx = new SimpleObjectProperty<>();
    private StringProperty quantity = new SimpleStringProperty();
    private StringProperty totalPrice = new SimpleStringProperty();



    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public String getPrice() {
        return price.get();
    }
    public StringProperty priceProperty() {
        return price;
    }
    public void setPrice(String price) {
        this.price.set(price);
    }
    public String getStock() {
        return stock.get();
    }
    public StringProperty stockProperty() {
        return stock;
    }
    public void setStock(String stock) {
        this.stock.set(stock);
    }
    public CategoryFx getCategoryFx() {
        return categoryFx.get();
    }
    public ObjectProperty<CategoryFx> categoryFxProperty() {
        return categoryFx;
    }
    public void setCategoryFx(CategoryFx categoryFx) {
        this.categoryFx.set(categoryFx);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getTotalPrice() {
        return totalPrice.get();
    }

    public StringProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice.set(totalPrice);
    }
}
