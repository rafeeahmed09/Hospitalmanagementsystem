package NewProject.example.hospital.management.system.Controls;

import NewProject.example.hospital.management.system.DTO.Appointment.AppointmentRequestDTO;
import NewProject.example.hospital.management.system.DTO.Appointment.AppointmentResponseDTO;
import NewProject.example.hospital.management.system.Service.AppointmentService;
import NewProject.example.hospital.management.system.Service.PatientServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Appointment")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientServices patientServices;

    @PostMapping("/patient-id/{patientId}")
    public ResponseEntity<AppointmentResponseDTO> CreateAppointment(
            @PathVariable Long patientId,
            @RequestBody AppointmentRequestDTO appointment
    ){
        AppointmentResponseDTO savedAppointment =
                appointmentService.createNewAppointment(patientId,appointment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedAppointment);
    }
    @PostMapping("/patient-name/{patientName}")
    public ResponseEntity<AppointmentResponseDTO> CreateAppointment(
            @PathVariable String patientName,
            @RequestBody AppointmentRequestDTO appointment
    ){
        AppointmentResponseDTO savedAppointment =
                appointmentService.createFirstnameAppointment(patientName,appointment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedAppointment);
    }
    @GetMapping("/patient-id/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatient(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        return ResponseEntity.ok(
                appointmentService.getAllListAppointment(
                        pageNumber,
                        pageSize,
                        patientId
                )
        );
    }
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointment(
            @RequestParam(defaultValue = "0") Integer pageNumbers,
            @RequestParam(defaultValue = "10") Integer pageSizes) {

        return ResponseEntity.ok(
                appointmentService.getListAppointment(
                        pageNumbers,
                        pageSizes
                ));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteAppointment(
            @PathVariable Long id
    ){
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(
                "Appointment with id " + id + "deleted successfully"
        );
    }

//    @DeleteMapping("/name/{firstName}")



}
