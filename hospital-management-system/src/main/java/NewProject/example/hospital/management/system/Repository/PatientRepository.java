package NewProject.example.hospital.management.system.Repository;

import NewProject.example.hospital.management.system.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
