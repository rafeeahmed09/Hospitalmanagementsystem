package NewProject.example.hospital.management.system.Service;

import NewProject.example.hospital.management.system.DTO.Appointment.AppointmentRequestDTO;
import NewProject.example.hospital.management.system.DTO.Appointment.AppointmentResponseDTO;
import NewProject.example.hospital.management.system.Entity.Appointment;
import NewProject.example.hospital.management.system.Entity.Doctor;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.AppointmentRepository;
import NewProject.example.hospital.management.system.Repository.DoctorRepository;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import NewProject.example.hospital.management.system.mapper.AppointmentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Appointment createAppointment(Appointment appointment, Long doctor_id, Long patient_id) throws IllegalAccessException {
        Doctor doctor = doctorRepository.findById(doctor_id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + doctor_id));
        Patient patient = patientRepository.findById(patient_id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + patient_id));
        if (appointment.getId()!= null) throw new IllegalAccessException("Appointment should not have");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointments().add(appointment);// to maintain consistency

        return appointmentRepository.save(appointment);
    }


    // PostMapping
    @Transactional
    public AppointmentResponseDTO createNewAppointment(
            Long patientId,
            AppointmentRequestDTO appointmentDTO) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Patient not found with id: " + patientId
                ));

        if (appointmentDTO.getDoctorId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "doctorId is required"
            );
        }

        if (appointmentDTO.getAppointmentDate() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "appointmentDate is required"
            );
        }

        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Doctor not found with id: " + appointmentDTO.getDoctorId()
                ));

        Appointment appointment = appointmentMapper
                .appointmentRequestDTOToAppointment(appointmentDTO);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        return appointmentMapper
                .appointmentToAppointmentResponseDTO(savedAppointment);
    }

   // GETwithPAtientId
    public List<AppointmentResponseDTO> getAllListAppointment(
            Integer pageNumber,
            Integer pageSize,
            Long patientId) {

        return appointmentRepository
                .findByPatientId(
                        patientId,
                        PageRequest.of(pageNumber, pageSize)
                )
                .getContent()
                .stream()
                .map(appointmentMapper::appointmentToAppointmentResponseDTO)
                .toList();
    }
    // OnlyGetALlListAppointment
    public List<AppointmentResponseDTO> getListAppointment(
            Integer pageNumber,
            Integer pageSize) {

        return appointmentRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .getContent()
                .stream()
                .map(appointmentMapper::appointmentToAppointmentResponseDTO)
                .toList();
    }
}
