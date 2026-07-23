package NewProject.example.hospital.management.system.DTO.Patient.Auth;

import NewProject.example.hospital.management.system.Entity.Type.BloodType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequestDTO {

    private Long id;
    private String firstName;
    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate birthDate;
    private String email;
    private String gender;
    private BloodType bloodGroup;
    private Boolean deleted;
    private String password;
    private String CreateDate;
}
