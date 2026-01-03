package net.javaguides.ems.mapper;

import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    @Test
    void testMapToEmployeeDto() {
        Employee emp = new Employee(1L, "John", "Doe", "john.doe@example.com");
        EmployeeDto dto = EmployeeMapper.mapToEmployeeDto(emp);

        assertNotNull(dto);
        assertEquals(emp.getId(), dto.getId());
        assertEquals(emp.getFirstName(), dto.getFirstName());
        assertEquals(emp.getLastName(), dto.getLastName());
        assertEquals(emp.getEmail(), dto.getEmail());
    }

    @Test
    void testMapToEmployee() {
        EmployeeDto dto = new EmployeeDto(2L, "Jane", "Smith", "jane.smith@example.com");
        Employee emp = EmployeeMapper.mapToEmployee(dto);

        assertNotNull(emp);
        assertEquals(dto.getId(), emp.getId());
        assertEquals(dto.getFirstName(), emp.getFirstName());
        assertEquals(dto.getLastName(), emp.getLastName());
        assertEquals(dto.getEmail(), emp.getEmail());
    }
}

