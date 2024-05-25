package co.istad.mbanking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account_types")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String alias; // payroll, saving-year
    private String name; // Payroll, Saving Year
    private String description;

    private Boolean isDeleted;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;

}
