package NewProject.example.hospital.management.system.PatientAllTestMethod;

import NewProject.example.hospital.management.system.DTO.Patient.PatientRequestDTO;
import NewProject.example.hospital.management.system.DTO.Patient.PatientResponseDTO;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Entity.Type.BloodType;

import java.time.LocalDate;

public class PatientsTestDataUtil {

    public PatientsTestDataUtil() {
    }

    public static Patient createPatientEntityA(){
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("jho Dpb");
        patient.setBirthDate(LocalDate.of(2000, 5, 10));
        patient.setEmail("test@gmail.com");
        patient.setGender("Male");
        patient.setBloodGroup(BloodType.O_Positive);
        return patient;

    }
    public static Patient createPatientEntityB(){
        Patient patient = new Patient();
        patient.setId(2L);
        patient.setFirstName("jho");
        patient.setBirthDate(LocalDate.of(2000, 5, 10));
        patient.setEmail("test@gmail.com");
        patient.setGender("Male");
        patient.setBloodGroup(BloodType.O_Positive);
        return patient;

    }
    public static PatientResponseDTO createPatientDTOC() {

        PatientResponseDTO patient = new PatientResponseDTO();

        patient.setId(3L);
        patient.setFirstName("jho DTO");
        patient.setBirthDate(LocalDate.of(2000, 5, 10));
        patient.setEmail("test@gmail.com");
        patient.setGender("Male");
        patient.setBloodGroup(BloodType.O_Positive);

        return patient;
    }
    public static PatientRequestDTO createPatientDTOD(){
        PatientRequestDTO patient = new PatientRequestDTO();
        patient.setId(4L);
        patient.setFirstName("jho DTO");
        patient.setBirthDate(LocalDate.of(2000, 5, 10));
        patient.setEmail("test@gmail.com");
        patient.setGender("Male");
        patient.setBloodGroup(BloodType.O_Positive);
        return patient;

    }


}
