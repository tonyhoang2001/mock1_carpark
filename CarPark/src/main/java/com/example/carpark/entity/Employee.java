package com.example.carpark.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long employeeId;

    @NotNull
    @Column(length = 50)
    @NotBlank(message = "Account cannot be blank!")
    private String account;

    @Column(length = 10)
    @NotBlank(message = "Department cannot be blank!")
    private String department;

    @Column(length = 50)
    @NotBlank(message = "Address cannot be blank!")
    private String employeeAddress;

    @NotNull(message = "DOB cannot be empty!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate employeeBirthdate;

    @Column(length = 50)
    @Email(message = "Email is wrong format!", regexp = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+")
    private String employeeEmail;

    @Column(length = 50)
    @NotBlank(message = "Name cannot be blank!")
    @Pattern(regexp = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$", message = "Invalid name!")
    private String employeeName;

    @Column(length = 10)
    @NotBlank(message = "Phone number cannot be blank!")
    @Length(min = 10, max = 10, message = "Phone must be 10 number!")
    private String employeePhone;

    @NotNull
    @Column(length = 20)
    @Length(min = 6, message = "Password must contain at least 6 characters!")
    private String password;

    @Column(length = 1)
    @NotBlank(message = "Sex cannot be blank!")
    private String sex;
}
