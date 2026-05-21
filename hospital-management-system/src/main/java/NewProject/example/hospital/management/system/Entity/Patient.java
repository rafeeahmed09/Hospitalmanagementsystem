package NewProject.example.hospital.management.system.Entity;

import NewProject.example.hospital.management.system.Entity.Type.BloodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "Patient",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_email",columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_name_birthdate",columnNames = {"first_name", "birth_date"})
        },
        indexes = {
                @Index(name = "idx_patient_birth_date",columnList = "birth_date")
        }
)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String firstName;

    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    private String email;

    private String gender;

     @CreationTimestamp
     @Column(updatable = false)
     private LocalDate createDate;

     @Enumerated(EnumType.STRING)
     private BloodType bloodGroup;

     @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
     private List<Insurance> insurances;

     @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
     private List<Appointment> appointments; //  inverse side
}
