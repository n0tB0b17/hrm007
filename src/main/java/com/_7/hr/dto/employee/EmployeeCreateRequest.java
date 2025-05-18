package com._7.hr.dto.employee;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeCreateRequest {
    @NotBlank(message = "first name cannot be blanked")
    @Size(min = 2, max = 20, message = "first name should be minimum of 2 and maximum of 20")
    private String firstName;
    @NotBlank(message = "first name cannot be blanked")
    @Size(min = 2, max = 20, message = "first name should be minimum of 2 and maximum of 20")
    private String lastName;

    @NotBlank(message = "email cannot be blanked")
    @Email(message = "email should be from a valid provider")
    private String email;

    @NotBlank(message = "job title cannot be blanked")
    @Size(min = 2, max = 20, message = "job title should be minimum of 2 and maximum of 20")
    private String jobTitle;

    @NotNull(message = "hire date cannot be null")
    @PastOrPresent(message = "hire date should be past or present")
    private LocalDate hireDate;
}
