package NewProject.example.hospital.management.system.mapper;

import NewProject.example.hospital.management.system.DTO.Patient.PatientRequestDTO;
import NewProject.example.hospital.management.system.DTO.Patient.PatientResponseDTO;
import NewProject.example.hospital.management.system.Entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientResponseDTO patientToPatientResponseDTO(Patient patient);

    Patient patientRequestDTOToPatient(PatientRequestDTO patientRequestDTO);

}


