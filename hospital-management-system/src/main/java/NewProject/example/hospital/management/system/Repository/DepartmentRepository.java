package NewProject.example.hospital.management.system.Repository;

import NewProject.example.hospital.management.system.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}