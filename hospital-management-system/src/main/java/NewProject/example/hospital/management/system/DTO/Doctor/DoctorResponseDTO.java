package NewProject.example.hospital.management.system.DTO.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponseDTO {
    private Long id;
    private String name;
    private String specialization;
    private String email;
    private Boolean deleted;
}
