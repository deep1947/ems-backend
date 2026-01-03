package net.javaguides.ems.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Column(name= "first_name")
    private String firstName;
@Column(name = "last_name")
    private String lastName;
@Column(name= "email",nullable = false,unique = true)
    private String email;

}

