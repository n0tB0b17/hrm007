package com._7.hr.dto.department;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentUpdateRequest {
    @Size(min = 2, max = 20, message = "department name cannot exceeds 20")
    private String name;

    @Size(max = 500, message = "department description cannot exceeds 500")
    private String description;
}
