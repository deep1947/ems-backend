package net.javaguides.ems.service.impl;


import lombok.AllArgsConstructor;
import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.entity.Employee;
import net.javaguides.ems.exception.ResourceNotFoundException;
import net.javaguides.ems.mapper.EmployeeMapper;
import net.javaguides.ems.repository.EmployeeRepository;
import net.javaguides.ems.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

//    @Override
//    public List<EmployeeDto> getAllEmployees() {
//        List<Employee> employee = employeeRepository.findAll();
//        return employees.stream()
//                .map(employee -> EmployeeMapper.mapToEmployeeDto(employee))
//        .collect(Collectors.toList());
//    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(employee -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
       Employee employee= employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

       employee.setFirstName(updatedEmployee.getFirstName());
         employee.setLastName(updatedEmployee.getLastName());
            employee.setEmailId(updatedEmployee.getEmailId());
         Employee updatedEmployeeObj= employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Page<EmployeeDto> getEmployeesPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return employeeRepository.findAll(pageable)
                .map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Page<EmployeeDto> searchEmployees(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employees;

        if (keyword == null || keyword.trim().isEmpty()) {
            employees = employeeRepository.findAll(pageable);
        } else {
            employees = employeeRepository
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailIdContainingIgnoreCase(
                            keyword, keyword, keyword, pageable
                    );
        }

        return employees.map(EmployeeMapper::mapToEmployeeDto);
    }



}
