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

//@Table(name = "contracts")
public class Contracts {
    @Id
    @Column(name = "contract_number")
    private Integer contract_code;
    @Column(name = "customer_name", nullable = true,length = -1)
    private String name;
    @Column(name = "contract_date", nullable = true, length = -1)
    private String regDate;
    @Column(name = "completion_date", nullable = true, length = -1)
    private String doneDate;
    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contracts contract = (Contracts) o;
        return Objects.equals(contract_code, contract.contract_code) && Objects.equals(name, contract.name) && Objects.equals(regDate, contract.regDate) && Objects.equals(doneDate, contract.doneDate);
    }
    @Override
    public int hashCode() {
        return Objects.hash(contract_code, name, regDate, doneDate);
    }

    public static List<String> columns() {
        return List.of("#", "# Contract","Customer Name","Registration date","Done date",
                "City","Street","Building number");
    }
    public List<String> getRawStringList() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add(contract_code.toString());
        list.add(name);
        list.add(regDate);
        list.add(doneDate);
        list.add(address.getCity());
        list.add(address.getStreet());
        list.add(address.getNBuild().toString());
        return list;
    }
}
