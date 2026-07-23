package NewProject.example.hospital.management.system.Service.Auth.Authservice;

import NewProject.example.hospital.management.system.DTO.Patient.Auth.PatientRequestDTO;
import NewProject.example.hospital.management.system.Service.SignupService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService implements AuthServiceable{

    private final SignupService signupService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PatientRequestDTO registerSignup(PatientRequestDTO patientRequestDTO) {
        return null;
    }
}
