package NewProject.example.hospital.management.system.Service;

import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientServices {

    @Autowired
    private final PatientRepository patientRepository;

    @Transactional
    public Patient getPatientById(Long id){
        Patient patient = patientRepository.findById(id).orElseThrow();

        return patient;
    }
}
