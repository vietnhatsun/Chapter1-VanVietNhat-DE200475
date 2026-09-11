package fu.de200475.pojo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate hireDate;

    private boolean active;

    @Transient
    private int yearsOfService;

    public Employee(String fullName, String email, BigDecimal salary, Gender gender, LocalDate hireDate) {
        this.fullName = fullName;
        this.email = email;
        this.salary = salary;
        this.gender = gender;
        this.hireDate = hireDate;
        this.active = true;
    }

    public int getYearsOfService() {
        if (hireDate == null) return 0;
        return Period.between(hireDate, LocalDate.now()).getYears();
    }
}