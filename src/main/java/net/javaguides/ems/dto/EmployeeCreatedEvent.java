package net.javaguides.ems.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor

public class EmployeeCreatedEvent implements Serializable {

    private String firstName;
    private String lastName;
    private String email;


    public EmployeeCreatedEvent(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }
}