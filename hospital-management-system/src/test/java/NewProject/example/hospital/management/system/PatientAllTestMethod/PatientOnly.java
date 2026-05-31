package NewProject.example.hospital.management.system.PatientAllTestMethod;

import NewProject.example.hospital.management.system.DTO.Patient.PatientRequestDTO;
import NewProject.example.hospital.management.system.DTO.Patient.PatientResponseDTO;
import NewProject.example.hospital.management.system.Entity.Type.BloodType;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import NewProject.example.hospital.management.system.Service.PatientServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class PatientOnly {

    private final PatientRepository patientRepository;

    private final PatientServices patientServices;

    @Autowired
    public PatientOnly(PatientRepository patientRepository, PatientServices patientServices) {
        this.patientRepository = patientRepository;
        this.patientServices = patientServices;
    }



    @Test
    void patientTest(){
       List<Patient> patients = patientRepository.findAll();
        System.out.println(patients);
        assertNotNull(patients);
    }



    @Test
    void createPatientWorks() {
        long patientCountBeforeCreate = patientRepository.count();

        PatientRequestDTO requestDTO = PatientRequestDTO.builder()
                .firstName("Test Patient")
                .birthDate(LocalDate.of(1999, 2, 11))
                .email("test.patient.post@example.com")
                .gender("Male")
                .bloodGroup(BloodType.A_Positive)
                .build();

        PatientResponseDTO responseDTO = patientServices.createNewPatient(requestDTO);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getId());
        assertEquals(patientCountBeforeCreate + 1, patientRepository.count());
    }
}
