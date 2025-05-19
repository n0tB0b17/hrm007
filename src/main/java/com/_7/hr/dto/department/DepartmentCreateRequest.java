package com._7.hr.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentCreateRequest {
    @NotBlank(message = "department name cannot be blanked")
    @Size(min = 1, max = 20, message = "")
    private String name;

    @Size(max = 500, message = "department description cannot exceeds 500 words")
    private String description;
}
