package NewProject.example.hospital.management.system.DTO.Insurance;


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
public class InsuranceResponseDTO {
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;
}
