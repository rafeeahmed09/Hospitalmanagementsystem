package NewProject.example.hospital.management.system.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false,unique = true, length = 100)
    private String policyNumber;

    @Column(nullable = false,length = 100)
    private String provider;
    private Boolean deleted;

    @Column(nullable = false)
    private LocalDate validUntil;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;

  @ManyToOne// inverse side
  @JoinColumn(name = "Patient_id")
  private Patient patient;



}
