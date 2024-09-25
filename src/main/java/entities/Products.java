package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Products {
    @Id
    private Integer product_code;
    @Column(name = "product_name", nullable = true,length = -1)
    private String name;
    private Double unit_price;
    //@Column(name = "workshop_id", nullable = true)
    //private Integer workshops_id;
    @ManyToOne
    @JoinColumn(name = "workshop_id", referencedColumnName = "workshop_id")
    private Workshops Workshops;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products product = (Products) o;
        return Objects.equals(product_code, product.product_code) && Objects.equals(name, product.name)  && Objects.equals(unit_price, product.unit_price);
    }
    @Override
    public int hashCode() {
        return Objects.hash(product_code, name, unit_price);
    }
    public static List<String> columns() {
        return List.of("#","Product code", "Product name", "Product cost", "Workshop name", "Workshop manager", "Workshop phone number");
    }

    public List<String> getRawStringList() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add(product_code.toString());
        list.add(name);
        list.add(unit_price.toString());
        list.add(Workshops.getName());
        list.add(Workshops.getManager());
        list.add(Workshops.getPhoneNum());
        return list;
    }
}