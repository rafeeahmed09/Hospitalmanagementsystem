package NewProject.example.hospital.management.system.Repository;

import NewProject.example.hospital.management.system.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmail(String email);

    Optional<Doctor> findByIdAndDeletedFalse(Long id);



}