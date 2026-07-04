package NewProject.example.hospital.management.system.Repository;

import NewProject.example.hospital.management.system.DTO.Patient.PatientRequestDTO;
import NewProject.example.hospital.management.system.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    boolean existsByEmail(String email);

    boolean existsByFirstNameAndBirthDate(String firstName, LocalDate birthDate);

    List<Patient> findByFirstName(String firstName);


    Optional<Patient> findByIdAndDeletedFalse(Long id);
}
