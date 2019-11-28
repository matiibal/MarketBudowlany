package utils;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class PieData {
    private String name;
    private BigDecimal quantity;
    @Override
    public String toString() {
        return "PieData{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}
