package NewProject.example.hospital.management.system.Repository;

import NewProject.example.hospital.management.system.Entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByPatientId(Long patientId, PageRequest pageable);

}