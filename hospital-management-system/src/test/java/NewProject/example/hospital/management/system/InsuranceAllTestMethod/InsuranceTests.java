/*package NewProject.example.hospital.management.system.InsuranceAllTestMethod;

import NewProject.example.hospital.management.system.Entity.Appointment;
import NewProject.example.hospital.management.system.Entity.Insurance;
import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Service.AppointmentService;
import NewProject.example.hospital.management.system.Service.InsuranceServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class InsuranceTests {

    @Autowired
    private InsuranceServices insuranceServices;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void TestInsurance(){
        Insurance insurance = Insurance.builder()
                .policyNumber("HDFC_1234")
                .provider("HDFC")
                .validUntil(LocalDate.of(2030,12,12))
                .build();

        Patient patient = insuranceServices.assignInsuranceToPatient(insurance,1L);
        System.out.println(patient);
    }


    @Test
    public void TestAppointment() throws IllegalAccessException {
        Appointment appointment = Appointment.builder()
                .appointmentDate(LocalDateTime.of(2025,11,1,14,00))
                .reason("Blooder Cancer")
                .build();
       Appointment newAppointment = appointmentService.createAppointment(appointment,1L,1L);
        System.out.println(newAppointment);
    }
}
    
*/
