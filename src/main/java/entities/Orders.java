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
@Getter
@Setter
public class Orders{
    @Id
    @Column(name = "order_id", nullable = true)
    private Integer order_id;
    private Integer quantity;
    //@Column(name = "contract_number", nullable = true)
    //private Integer contractNum;
    @Column(name = "product_code", nullable = true)
    private Integer productCode;
    @ManyToOne
    @JoinColumn(name = "contract_number", referencedColumnName = "contract_number")
    private Contracts Contracts;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders order = (Orders) o;
        return Objects.equals(order_id, order.order_id) &&  Objects.equals(quantity, order.quantity)  &&  Objects.equals(productCode, order.productCode);
    }
    @Override
    public int hashCode() {
        return Objects.hash(order_id, quantity, productCode);
    }

    public static List<String> columns() {
        return List.of("#", "quantity", "Product code", "customer_name","contract date", "done date");
    }
    public List<String> getRawStringList() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add(quantity.toString());
        list.add(productCode.toString());
        list.add(Contracts.getName());
        list.add(Contracts.getRegDate());
        list.add(Contracts.getDoneDate());
        return list;
    }
}
