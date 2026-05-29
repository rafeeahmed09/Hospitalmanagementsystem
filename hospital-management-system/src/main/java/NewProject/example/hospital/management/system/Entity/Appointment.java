package NewProject.example.hospital.management.system.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;


import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter
@Builder
public class  Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "patient_id",nullable = false) // Owing side , patient is required and not nullable
    private Patient patient;

    @ManyToOne
    @JoinColumn(nullable = false, name ="Doctor_id")
    private Doctor doctor;
}
