package database.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.util.Precision;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
public class Orders {
    @Id
    @GeneratedValue

    private int id;
   private LocalDate orderDate;
   @ManyToOne()
   private Client client;

    @ElementCollection
    @CollectionTable(name="order_details",
            joinColumns=@JoinColumn(name="order_id"))
    @MapKeyJoinColumn(name="item_id")
    @Column(name="qty")
    private Map<BuiltItems, Integer> item;

    private double totalPrice;

    public void Order() {

        totalPrice = 0;


    }


    public Double total(double price, int quantity)
    {
        totalPrice =totalPrice + (price*quantity);
        return Precision.round(totalPrice,2);
    }


    public Double totalAfterRemoving(Double price, int quantity) {
    totalPrice =totalPrice-(price*quantity);
    return Precision.round(totalPrice,2);
    }
}
