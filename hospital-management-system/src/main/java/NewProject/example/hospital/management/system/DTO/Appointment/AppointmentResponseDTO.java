package NewProject.example.hospital.management.system.DTO.Appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDateTime;



@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponseDTO {

    private Long id;
    private LocalDateTime appointmentDate;
    private String reason;
    private Boolean deleted;
}
