package NewProject.example.hospital.management.system.Service;

import NewProject.example.hospital.management.system.DTO.Insurance.InsuranceRequestDTOs;
import NewProject.example.hospital.management.system.DTO.Insurance.InsuranceResponseDTO;
import NewProject.example.hospital.management.system.Entity.Insurance;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.InsuranceRepository;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import NewProject.example.hospital.management.system.mapper.InsuranceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;




@Service
@RequiredArgsConstructor
public class InsuranceServices {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private final InsuranceMapper insuranceMapper;


    public InsuranceResponseDTO creteNewInusrande(Long patientId, InsuranceRequestDTOs insuranceRequestDTOs) {
        if (insuranceRequestDTOs.getPolicyNumber() == null || insuranceRequestDTOs.getPolicyNumber().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "PolicyNumber is required"
            );
        }

        if (insuranceRequestDTOs.getProvider() == null || insuranceRequestDTOs.getProvider().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Provider is required"
            );
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new  ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Patient not found with id: " + patientId
                ));

        if (insuranceRequestDTOs.getPatientId() != null && !insuranceRequestDTOs.getPatientId().equals(patientId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "PatientId in path and body must match"
            );
        }

        insuranceRequestDTOs.setPatientId(patientId);

        Insurance insurance = insuranceMapper
                .insuranceRequestDTOToinsurance(insuranceRequestDTOs);

        insurance.setPatient(patient);

        Insurance savedIn =
                insuranceRepository.save(insurance);

        return insuranceMapper
                .insuranceToAppointmentResponseDTO(savedIn);
    }
}
