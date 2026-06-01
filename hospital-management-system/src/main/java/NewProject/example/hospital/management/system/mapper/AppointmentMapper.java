package NewProject.example.hospital.management.system.mapper;

import NewProject.example.hospital.management.system.DTO.Appointment.AppointmentResponseDTO;
import NewProject.example.hospital.management.system.DTO.Appointment.AppointmentRequestDTO;
import NewProject.example.hospital.management.system.Entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    AppointmentResponseDTO appointmentToAppointmentResponseDTO(Appointment appointment);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    Appointment appointmentRequestDTOToAppointment(AppointmentRequestDTO appointmentRequestDTO);
}
