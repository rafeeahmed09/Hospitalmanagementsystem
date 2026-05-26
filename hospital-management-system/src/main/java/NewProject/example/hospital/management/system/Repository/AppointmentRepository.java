package NewProject.example.hospital.management.system.Repository;

import NewProject.example.hospital.management.system.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}