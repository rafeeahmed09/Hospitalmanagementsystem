package NewProject.example.hospital.management.system.Controls;


import NewProject.example.hospital.management.system.DTO.Insurance.InsuranceRequestDTOs;
import NewProject.example.hospital.management.system.DTO.Insurance.InsuranceResponseDTO;
import NewProject.example.hospital.management.system.Service.InsuranceServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceServices insuranceServices;

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<InsuranceResponseDTO> CreateInsurance(
            @PathVariable Long patientId,
            @RequestBody InsuranceRequestDTOs insuranceRequestDTOs
    ){
        InsuranceResponseDTO savedInsurance = insuranceServices.creteNewInusrande(patientId,insuranceRequestDTOs);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedInsurance);
    }

}
