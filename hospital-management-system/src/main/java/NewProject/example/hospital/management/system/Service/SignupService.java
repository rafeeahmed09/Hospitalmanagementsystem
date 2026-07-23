package NewProject.example.hospital.management.system.Service;

import NewProject.example.hospital.management.system.DTO.Patient.Auth.PatientRequestDTO;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import NewProject.example.hospital.management.system.mapper.PatientMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Service;

import java.security.Provider;

@Service
@AllArgsConstructor
public class SignupService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientRequestDTO createSignup(PatientRequestDTO requestDTO) {
        if(requestDTO.getEmail() == null || requestDTO.getEmail().isBlank()){
            throw  new IllegalArgumentException( "Email is required");
        }
        if (patientRepository.existsByEmail(requestDTO.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        Patient patient  = patientMapper.map(requestDTO,Patient.class);
        patient.setCreateDate(requestDTO.setCreateDate()!=null ? requestDTO.getCreateDate() : CreatedDate.class);
        Patient SaveSignup = patientRepository.save(patient);

        return patientMapper.map(SaveSignup,PatientRequestDTO.class);
    }


}
