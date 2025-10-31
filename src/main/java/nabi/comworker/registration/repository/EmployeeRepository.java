package nabi.comworker.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nabi.comworker.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
}
