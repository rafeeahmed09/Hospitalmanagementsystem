package NewProject.example.hospital.management.system.mapper;

import NewProject.example.hospital.management.system.DTO.Doctor.DoctorRequestDTO;
import NewProject.example.hospital.management.system.DTO.Doctor.DoctorResponseDTO;
import NewProject.example.hospital.management.system.Entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    DoctorResponseDTO DOCTOR_RESPONSE_DTO(Doctor doctor);

    Doctor DOCTORRequestDTOTODoctor(DoctorRequestDTO doctorRequestDTO);
}
