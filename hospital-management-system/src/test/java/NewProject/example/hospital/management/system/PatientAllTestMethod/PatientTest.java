package NewProject.example.hospital.management.system.PatientAllTestMethod;

import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Repository.PatientRepository;
import NewProject.example.hospital.management.system.Service.PatientServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PatientTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientServices patientServices;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void createPatientCreatedEntity() throws Exception{
        Patient testPatient = PatientsTestDataUtil.createPatientEntityA();
        testPatient.setId(null);

        mockMvc.perform(post("/Patient_Api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPatient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value(testPatient.getFirstName()))
                .andExpect(jsonPath("$.email").value(testPatient.getEmail()))
                .andExpect(jsonPath("$.gender").value(testPatient.getGender()))
                .andExpect(jsonPath("$.bloodGroup")
                        .value(testPatient.getBloodGroup().name()));

    }
}
