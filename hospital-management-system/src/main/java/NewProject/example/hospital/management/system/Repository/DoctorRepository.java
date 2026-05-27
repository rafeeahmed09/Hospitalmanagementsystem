package NewProject.example.hospital.management.system.Repository;

import NewProject.example.hospital.management.system.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}