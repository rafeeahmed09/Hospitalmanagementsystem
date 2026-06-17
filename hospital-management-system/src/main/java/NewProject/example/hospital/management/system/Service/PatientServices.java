package NewProject.example.hospital.management.system.Service;

import NewProject.example.hospital.management.system.DTO.Patient.PatientRequestDTO;
import NewProject.example.hospital.management.system.DTO.Patient.PatientResponseDTO;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Entity.Type.BloodType;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import NewProject.example.hospital.management.system.mapper.PatientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServices {

    @Autowired
    private final PatientRepository patientRepository;
    @Autowired
    private final PatientMapper patientMapper;

    @Transactional
    public List<PatientResponseDTO> getALLPatient(Integer pageNumber,Integer pageSIze){
        return patientRepository.findAll(PageRequest.of(pageNumber,pageSIze))
                .stream()
                .map(patientMapper::patientToPatientResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public Patient getPatientById(Long id){
             return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + id));
    }

    @Transactional
    public PatientResponseDTO createNewPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRequestDTO.getFirstName() == null || patientRequestDTO.getFirstName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }

        if (patientRequestDTO.getEmail() == null || patientRequestDTO.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        boolean emailExists = patientRepository.existsByEmail(patientRequestDTO.getEmail());

        if (emailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Patient email already exists");
        }

        if (patientRequestDTO.getBirthDate() != null) {
            boolean patientExists = patientRepository.existsByFirstNameAndBirthDate(
                    patientRequestDTO.getFirstName(), patientRequestDTO.getBirthDate()
            );

            if (patientExists) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Patient with same first name and birth date already exists");
            }
        }

        Patient newPatient = patientMapper.patientRequestDTOToPatient(patientRequestDTO);
        Patient savedPatient = patientRepository.save(newPatient);

        return patientMapper.patientToPatientResponseDTO(savedPatient);
    }

    @Transactional
    public List<PatientResponseDTO> createMultiplePatients(List<PatientRequestDTO> patientRequestDTOs) {

        List<Patient> patients = patientRequestDTOs.stream()
                .map(patientMapper::patientRequestDTOToPatient)
                .collect(Collectors.toList());

        List<Patient> savedPatients = patientRepository.saveAll(patients);

        return savedPatients.stream()
                .map(patientMapper::patientToPatientResponseDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found with id: " + id));
        patientRepository.delete(patient);
    }

    @Transactional
    public void deleteByName(String firstName) {
        List<Patient> patients = patientRepository.findByFirstName(firstName);

        if (patients.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No patients found with name: " + firstName);
        }

        patientRepository.deleteAll(patients);
    }

    @Transactional
    public PatientResponseDTO updateOldPatient(Long id, PatientRequestDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found with id: " + id));
        
        if (dto.getFirstName() != null && !dto.getFirstName().trim().isEmpty()) {
            patient.setFirstName(dto.getFirstName());
        }
        if (dto.getBirthDate() != null) {
            patient.setBirthDate(dto.getBirthDate());
        }
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            if (!patient.getEmail().equals(dto.getEmail()) && patientRepository.existsByEmail(dto.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use by another patient");
            }
            patient.setEmail(dto.getEmail());
        }
        if (dto.getGender() != null) {
            patient.setGender(dto.getGender());
        }
        if (dto.getBloodGroup() != null) {
            patient.setBloodGroup(dto.getBloodGroup());
        }

        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.patientToPatientResponseDTO(updatedPatient);
    }

    @Transactional
    public PatientResponseDTO updatePatientPartially(Long id, Map<String, Object> updates) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    patient.setFirstName((String) value);
                    break;
                case "birthDate":
                    patient.setBirthDate(LocalDate.parse((String) value));
                    break;
                case "email":
                    patient.setEmail((String) value);
                    break;
                case "gender":
                    patient.setGender((String) value);
                    break;
                case "bloodGroup":
                    if (value != null) {
                        String enumValue = ((String) value).toUpperCase().trim();
                        try {
                            patient.setBloodGroup(BloodType.valueOf(enumValue));
                        } catch (IllegalArgumentException e) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid blood group: " + value);
                        }
                    } else {
                        patient.setBloodGroup(null);
                    }
                    break;
            }
        });
        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.patientToPatientResponseDTO(updatedPatient);
    }
}
