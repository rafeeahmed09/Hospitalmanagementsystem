package NewProject.example.hospital.management.system.Controls.AuthControls;

import NewProject.example.hospital.management.system.DTO.Patient.Auth.PatientRequestDTO;
import NewProject.example.hospital.management.system.Service.SignupService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Patient")
@AllArgsConstructor
public class SignupControls {

    @Autowired
    private final SignupService signupService;

    @PostMapping("/Signup")
    public ResponseEntity<PatientRequestDTO> createNewSignup(PatientRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(
              signupService.createSignup(requestDTO)
        );
    }
}
