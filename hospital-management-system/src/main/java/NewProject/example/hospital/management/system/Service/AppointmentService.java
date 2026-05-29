package NewProject.example.hospital.management.system.Service;

import NewProject.example.hospital.management.system.Entity.Appointment;
import NewProject.example.hospital.management.system.Entity.Doctor;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.AppointmentRepository;
import NewProject.example.hospital.management.system.Repository.DoctorRepository;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
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


}
