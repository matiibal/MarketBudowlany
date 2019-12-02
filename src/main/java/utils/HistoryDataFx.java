package utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryDataFx {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty surnameColumn = new SimpleStringProperty();
    private StringProperty dateColumn = new SimpleStringProperty();
    private StringProperty nipColumn = new SimpleStringProperty();
    private StringProperty totalColumn = new SimpleStringProperty();


    public String getSurnameColumn() {
        return surnameColumn.get();
    }

    public StringProperty surnameColumnProperty() {
        return surnameColumn;
    }

    public void setSurnameColumn(String surnameColumn) {
        this.surnameColumn.set(surnameColumn);
    }

    public String getDateColumn() {
        return dateColumn.get();
    }

    public StringProperty dateColumnProperty() {
        return dateColumn;
    }

    public void setDateColumn(String dateColumn) {
        this.dateColumn.set(dateColumn);
    }

    public String getNipColumn() {
        return nipColumn.get();
    }

    public StringProperty nipColumnProperty() {
        return nipColumn;
    }

    public void setNipColumn(String nipColumn) {
        this.nipColumn.set(nipColumn);
    }

    public String getTotalColumn() {
        return totalColumn.get();
    }

    public StringProperty totalColumnProperty() {
        return totalColumn;
    }

    public void setTotalColumn(String totalColumn) {
        this.totalColumn.set(totalColumn);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
}
