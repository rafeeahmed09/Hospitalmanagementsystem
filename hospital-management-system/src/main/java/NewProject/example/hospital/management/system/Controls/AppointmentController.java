package NewProject.example.hospital.management.system.Controls;

import NewProject.example.hospital.management.system.DTO.Appointment.AppointmentRequestDTO;
import NewProject.example.hospital.management.system.DTO.Appointment.AppointmentResponseDTO;
import NewProject.example.hospital.management.system.Service.AppointmentService;
import NewProject.example.hospital.management.system.Service.PatientServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Appointment")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientServices patientServices;

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<AppointmentResponseDTO> CreateAppointment(
            @PathVariable Long patientId,
            @RequestBody AppointmentRequestDTO appointment
    ){
        AppointmentResponseDTO savedAppointment =
                appointmentService.createNewAppointment(patientId,appointment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedAppointment);
    }
}
