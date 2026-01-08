package net.javaguides.ems.repository;

import net.javaguides.ems.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Page<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailIdContainingIgnoreCase(
            String firstName,
            String lastName,
            String emailId,
            Pageable pageable
    );
}
