package uz.pdp.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = true)
    private String phoneNumber;

    private String password;

    @JsonIgnore
    private String code = UUID.randomUUID().toString();

    @JsonIgnore
    private boolean active = true;

    @ManyToMany
    private Set<Warehouse> warehouses;
}
