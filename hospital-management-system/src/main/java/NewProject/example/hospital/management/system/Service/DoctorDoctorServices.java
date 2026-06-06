package NewProject.example.hospital.management.system.Service;

import NewProject.example.hospital.management.system.DTO.Doctor.DoctorRequestDTO;
import NewProject.example.hospital.management.system.DTO.Doctor.DoctorResponseDTO;
import NewProject.example.hospital.management.system.Entity.Doctor;
import NewProject.example.hospital.management.system.Repository.DoctorRepository;
import NewProject.example.hospital.management.system.mapper.DoctorMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DoctorDoctorServices {

    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final DoctorMapper doctorMapper;


    @Transactional
    public DoctorResponseDTO createNewDoctor(DoctorRequestDTO doctorRequestDTO) {

        if (doctorRequestDTO.getName() == null || doctorRequestDTO.getName().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Name name is required");
        }

        if (doctorRequestDTO.getEmail() == null || doctorRequestDTO.getEmail().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        boolean emailExists = doctorRepository.existsByEmail(doctorRequestDTO.getEmail());

        if (emailExists){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Doctor email already exists");
        }
        if (doctorRequestDTO.getSpecialization() == null || doctorRequestDTO.getSpecialization().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specialization is required");

        }

        Doctor newDoctor = doctorMapper.DOCTORRequestDTOTODoctor(doctorRequestDTO);
        Doctor savedDoctor = doctorRepository.save(newDoctor);

        return doctorMapper.DOCTOR_RESPONSE_DTO(savedDoctor);
    }

    @Transactional
    public Doctor getDoctorAll(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Doctor not found with id: " + doctorId
                        ));
    }
}
