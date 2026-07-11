package NewProject.example.hospital.management.system.Controls;

import NewProject.example.hospital.management.system.DTO.Patient.PatientRequestDTO;
import NewProject.example.hospital.management.system.DTO.Patient.PatientResponseDTO;
import NewProject.example.hospital.management.system.Service.PatientServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Patient_Api")
@AllArgsConstructor
public class PatientController {

    private final PatientServices patientServices;


    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            @RequestBody PatientRequestDTO patientRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientServices.createNewPatient(patientRequestDTO)
                );
    }

    @PostMapping("/MultiplePatient")
    public ResponseEntity<List<PatientResponseDTO>> createMultiplePatients(
            @RequestBody List<PatientRequestDTO> patientRequestDTOs) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientServices.createMultiplePatients(patientRequestDTOs));
    }


    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponseDTO> getPatientProfile(@PathVariable Long patientId){
        PatientResponseDTO patient = patientServices.getPatientById(patientId);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok(patientServices.getAllPatient());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientServices.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/name/{firstName}")
    public ResponseEntity<Void> deletePatientsByName(
            @PathVariable String firstName) {
        patientServices.deleteByName(firstName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO updatedPatient = patientServices.updateOldPatient(id, patientRequestDTO);
        return ResponseEntity.ok(updatedPatient);
    }

    @PatchMapping("/deleted-soft/{id}")
    public  ResponseEntity<Void> deletedSoft(@PathVariable Long id){
        if(patientServices.deleteBySoft(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
