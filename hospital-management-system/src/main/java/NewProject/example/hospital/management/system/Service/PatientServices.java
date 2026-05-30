package NewProject.example.hospital.management.system.Service;

import NewProject.example.hospital.management.system.DTO.PatientRequestDTO;
import NewProject.example.hospital.management.system.DTO.PatientResponseDTO;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PatientServices {

    private  final PatientRepository patientRepository;
    private final ModelMapper modelMapper;


    public List<PatientResponseDTO> getALLPatient(Integer pageNumber,Integer pageSIze){
        return patientRepository.findAll(PageRequest.of(pageNumber,pageSIze))
                .stream()
                .map(patient -> modelMapper.map(patient,PatientResponseDTO.class))
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

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "First name is required"
            );
        }


        if (patientRequestDTO.getEmail() == null || patientRequestDTO.getEmail().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email is required"
            );
        }


        boolean emailExists = patientRepository.existsByEmail(
                        patientRequestDTO.getEmail()
                );

        if (emailExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Patient email already exists"
            );
        }


        if (patientRequestDTO.getBirthDate() != null) {

            boolean patientExists =
                    patientRepository.existsByFirstNameAndBirthDate(
                            patientRequestDTO.getFirstName(),
                            patientRequestDTO.getBirthDate()
                    );

            if (patientExists) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Patient with same first name and birth date already exists"
                );
            }
        }


        Patient newPatient =  modelMapper.map(patientRequestDTO, Patient.class);


        Patient savedPatient = patientRepository.save(newPatient);


        return modelMapper.map(
                savedPatient,
                PatientResponseDTO.class
        );
    }

    @Transactional
    public List<PatientResponseDTO> createMultiplePatients(List<PatientRequestDTO> patientRequestDTOs) {



        List<Patient> patients = patientRequestDTOs.stream()
                .map(dto -> modelMapper.map(dto, Patient.class))
                .collect(Collectors.toList());

        List<Patient> savedPatients = patientRepository.saveAll(patients);

        return savedPatients.stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDTO.class))
                .collect(Collectors.toList());
    }

    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Patient not found with id: " + id
                ));
        patientRepository.delete(patient);
    }

    public void deleteByName(String firstName) {

        List<Patient> patients = patientRepository.findByFirstName(firstName);

        if (patients.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No patients found with name: " + firstName
            );
        }

        patientRepository.deleteAll(patients);
    }
}
