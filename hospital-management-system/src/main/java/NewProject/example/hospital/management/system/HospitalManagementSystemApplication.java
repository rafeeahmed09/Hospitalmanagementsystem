package NewProject.example.hospital.management.system;

import org.hibernate.dialect.function.array.JsonArrayViaElementArgumentReturnTypeResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class HospitalManagementSystemApplication {



	public static void main(String[] args) {


//        System.out.println("Users Password : " + new BCryptPasswordEncoder().encode("Users2341"));
      //  System.out.println("Admin Password : " + new BCryptPasswordEncoder().encode("Admin123"));

		SpringApplication.run(HospitalManagementSystemApplication.class, args);
	}

}