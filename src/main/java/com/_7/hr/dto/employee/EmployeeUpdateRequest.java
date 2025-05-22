package com._7.hr.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeUpdateRequest {
    @Size(min = 2, max = 20, message = "employee first name should not be blanked")
    private String firstName;

    @Size(min = 2, max = 20, message = "employee last name should not be blanked")
    private String lastName;

    @Email(message = "email should be from a valid provider")
    private String email;

    @Size(min = 2, max = 20, message = "job title should be minimum of 2 and maximum of 20")
    private String jobTitle;
}
