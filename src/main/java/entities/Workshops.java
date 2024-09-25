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

public class Workshops {
    @Id
    @Column(name = "workshop_id")
    private Integer workshop_id;
    @Column(name = "workshop_name", nullable = true,length = -1)
    private String name;
    @Column(name = "workshop_manager", nullable = true,length = -1)
    private String manager;
    @Column(name = "workshop_phone", nullable = true, length = -1)
    private String phoneNum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workshops workshop = (Workshops) o;
        return Objects.equals(workshop_id, workshop.workshop_id) && Objects.equals(name, workshop.name) && Objects.equals(manager, workshop.manager) && Objects.equals(phoneNum, workshop.phoneNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workshop_id, name, manager, phoneNum);
    }

    public static List<String> columns() {
        return List.of("#","Workshop number", "Workshop name", "Workshop manager", "Workshop phone");
    }
    public List<String> getRawStringList() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add(workshop_id.toString());
        list.add(name);
        list.add(manager);
        list.add(phoneNum);
        return list;
    }

}
