package NewProject.example.hospital.management.system.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter

@Getter
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(length = 100)
    private String specialization;
    private Boolean deleted;

    @Column(nullable = false,unique = true,length = 100)
    private String email;

    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departmentSet = new HashSet<>();

}
