package net.javaguides.ems.service;

import net.javaguides.ems.dto.EmployeeDto;
import org.springframework.data.domain.Page;

import java.util.List;



public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long id);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long employeeid, EmployeeDto updatedEmployee);

    void deleteEmployee(Long employeeId);

    Page<EmployeeDto> getEmployeesPaged(int page, int size);

    Page<EmployeeDto> searchEmployees(String keyword, int page, int size, String sortBy, String direction
    );
}
