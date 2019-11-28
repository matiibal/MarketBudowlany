package database.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
public class BuiltItems {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String name;
    private double price;
    private int stock;
    @ManyToOne
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuiltItems that = (BuiltItems) o;
        return

                Objects.equals(name, that.name) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, stock, category);
    }
}
