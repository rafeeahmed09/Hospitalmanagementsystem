package NewProject.example.hospital.management.system;

import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import NewProject.example.hospital.management.system.Service.PatientServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class PatientTests {

    private final PatientRepository patientRepository;

    private final PatientServices patientServices;

    @Autowired
    public PatientTests(PatientRepository patientRepository, PatientServices patientServices) {
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
    void patientTestGetById(){

    }
}
