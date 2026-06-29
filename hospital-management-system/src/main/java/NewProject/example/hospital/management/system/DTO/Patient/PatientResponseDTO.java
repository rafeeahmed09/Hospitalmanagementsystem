package NewProject.example.hospital.management.system.DTO.Patient;

import NewProject.example.hospital.management.system.Entity.Type.BloodType;


import java.time.LocalDate;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponseDTO {

    private Long id;
    private String firstName;
    private LocalDate birthDate;
    private String email;
    private String gender;
    private LocalDate createDate;
    private BloodType bloodGroup;
}
