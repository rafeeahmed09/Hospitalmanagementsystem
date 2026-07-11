package NewProject.example.hospital.management.system.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import NewProject.example.hospital.management.system.DTO.Patient.PatientRequestDTO;
import NewProject.example.hospital.management.system.DTO.Patient.PatientResponseDTO;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import NewProject.example.hospital.management.system.exception.DuplicateResourceException;
import NewProject.example.hospital.management.system.exception.ResourceNotFoundException;
import NewProject.example.hospital.management.system.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServices {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Transactional
    public List<PatientResponseDTO> getAllPatient() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::patientToPatientResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PatientResponseDTO getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::patientToPatientResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with id " + id + " not found"));
    }

    @Transactional
    public PatientResponseDTO createNewPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRequestDTO.getFirstName() == null || patientRequestDTO.getFirstName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }

        if (emailExists(patientRequestDTO.getEmail())) {
            throw new DuplicateResourceException("Patient with email " + patientRequestDTO.getEmail() +
                    " already exists");
        }

        if (patientRequestDTO.getBirthDate() != null && patientRepository.existsByFirstNameAndBirthDate(
                patientRequestDTO.getFirstName(), patientRequestDTO.getBirthDate()
        )) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient with same first name and birth date already exists");
        }

        Patient newPatient = patientMapper.patientRequestDTOToPatient(patientRequestDTO);
        newPatient.setDeleted(false);
        Patient savedPatient = patientRepository.save(newPatient);

        return patientMapper.patientToPatientResponseDTO(savedPatient);
    }

    @Transactional
    public List<PatientResponseDTO> createMultiplePatients(List<PatientRequestDTO> patientRequestDTOs) {
        Set<String> emailsInBatch = new HashSet<>();
        // Validation pass
        for (PatientRequestDTO dto : patientRequestDTOs) {
            if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required for all patients in the batch.");
            }
            if (dto.getEmail() == null || !emailsInBatch.add(dto.getEmail())) {
                throw new DuplicateResourceException("Duplicate email found in batch: " + dto.getEmail());
            }
            if (emailExists(dto.getEmail())) {
                throw new DuplicateResourceException("Patient with email " + dto.getEmail() + " already exists.");
            }
        }

        List<Patient> patients = patientRequestDTOs.stream()
                .map(patientMapper::patientRequestDTOToPatient)
                .peek(patient -> patient.setDeleted(false))
                .collect(Collectors.toList());

        List<Patient> savedPatients = patientRepository.saveAll(patients);

        return savedPatients.stream()
                .map(patientMapper::patientToPatientResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient with id " + id + " not found");
        }
        patientRepository.deleteById(id);
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
                .orElseThrow(() -> new ResourceNotFoundException("Patient with id " + id + " not found"));

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
    public boolean deleteBySoft(Long id) {
        return patientRepository.findByIdAndDeletedFalse(id)
                .map(patient -> {
                    patient.setDeleted(true);
                    patientRepository.save(patient);
                    return true;
                }).orElse(false);
    }

    private boolean emailExists(String email) {
        return email != null && patientRepository.existsByEmail(email);
    }
}
