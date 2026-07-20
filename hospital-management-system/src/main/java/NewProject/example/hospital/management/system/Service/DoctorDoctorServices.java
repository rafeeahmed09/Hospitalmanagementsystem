package NewProject.example.hospital.management.system.Service;

import NewProject.example.hospital.management.system.DTO.Doctor.DoctorRequestDTO;
import NewProject.example.hospital.management.system.DTO.Doctor.DoctorResponseDTO;
import NewProject.example.hospital.management.system.Entity.Doctor;
import NewProject.example.hospital.management.system.Repository.DoctorRepository;
import NewProject.example.hospital.management.system.exception.ResourceNotFoundException;
import NewProject.example.hospital.management.system.mapper.DoctorMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorDoctorServices {

    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final DoctorMapper doctorMapper;

    @Transactional
    public DoctorResponseDTO createNewDoctor(DoctorRequestDTO doctorRequestDTO) {

        if (doctorRequestDTO.getName() == null || doctorRequestDTO.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name name is required");
        }

        if (doctorRequestDTO.getEmail() == null || doctorRequestDTO.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        boolean emailExists = doctorRepository.existsByEmail(doctorRequestDTO.getEmail());

        if (emailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Doctor email already exists");
        }
        if (doctorRequestDTO.getSpecialization() == null || doctorRequestDTO.getSpecialization().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specialization is required");

        }

        Doctor newDoctor = doctorMapper.DOCTORRequestDTOTODoctor(doctorRequestDTO);
        newDoctor.setDeleted(false);
        Doctor savedDoctor = doctorRepository.save(newDoctor);

        return doctorMapper.DOCTOR_RESPONSE_DTO(savedDoctor);
    }

    @Transactional(readOnly = true)
    public DoctorResponseDTO getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .map(doctorMapper::DOCTOR_RESPONSE_DTO)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with id " + doctorId + " not found"));
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::DOCTOR_RESPONSE_DTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDoctorById(long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    @Transactional
    public Boolean deleteBYSoft(Long id) {
        Optional<Doctor> doctor = doctorRepository.findByIdAndDeletedFalse(id);
        if (doctor.isEmpty()) {
            return false;
        }
        Doctor doctor1 = doctor.get();
        doctor1.setDeleted(true);

        doctorRepository.save(doctor1);

        return true;
    }
}
