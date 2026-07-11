package NewProject.example.hospital.management.system.Controls;


import NewProject.example.hospital.management.system.DTO.Doctor.DoctorRequestDTO;
import NewProject.example.hospital.management.system.DTO.Doctor.DoctorResponseDTO;
import NewProject.example.hospital.management.system.Service.DoctorDoctorServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Doctor_Api")
@AllArgsConstructor
public class DoctorController {

    private final DoctorDoctorServices doctorDoctorServices;



    @GetMapping("/Doctor_id/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> findDoctorById(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                doctorDoctorServices.getDoctorById(doctorId)
        );
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(
            @RequestBody DoctorRequestDTO doctorRequestDTO) {

        DoctorResponseDTO response =
                doctorDoctorServices.createNewDoctor(doctorRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorDoctorServices.getAllDoctors());
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deletedDoctor(@PathVariable long id){
        doctorDoctorServices.deleteDoctorById(id);
        return ResponseEntity.ok(
                "Doctor   deleted   Successfully   with    id    :    " +      id
        );
    }


    @PatchMapping("/deleted-soft/{id}")
    public ResponseEntity<String> deletedSoft(@PathVariable Long id){
        Boolean isDeleted = doctorDoctorServices.deleteBYSoft(id);
        if(isDeleted){
            return ResponseEntity.ok("Record deleted");
        }

        return ResponseEntity.notFound().build();
    }

}
