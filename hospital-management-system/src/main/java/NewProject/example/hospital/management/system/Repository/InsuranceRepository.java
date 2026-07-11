package NewProject.example.hospital.management.system.Repository;

import NewProject.example.hospital.management.system.Entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}