package NewProject.example.hospital.management.system.mapper;


import NewProject.example.hospital.management.system.DTO.Insurance.InsuranceRequestDTOs;
import NewProject.example.hospital.management.system.DTO.Insurance.InsuranceResponseDTO;
import NewProject.example.hospital.management.system.Entity.Insurance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InsuranceMapper {

    InsuranceMapper INSURANCE_MAPPER = Mappers.getMapper(InsuranceMapper.class);


    InsuranceResponseDTO insuranceToAppointmentResponseDTO(Insurance insurance);

    Insurance  insuranceRequestDTOToinsurance(InsuranceRequestDTOs insuranceResponseDTO);
}
