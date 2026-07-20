package NewProject.example.hospital.management.system.DTO.Patient.Auth;

import NewProject.example.hospital.management.system.Entity.Type.BloodType;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PatientDTO {

    private Long id;
    private String firstName;
    private LocalDate birthDate;
    private String email;
    @NotBlank
    @NotNull
    @JsonProperty("password")
    @JsonAlias({"Password"})
    private String password;
    private String gender;
    private BloodType bloodGroup;
    private Boolean deleted;
}
