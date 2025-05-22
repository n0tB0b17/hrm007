package com._7.hr.dto.role;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleUpdateRequest {
    @Size(min = 3, max = 20, message = "role not should be minimum of 3 and maximum of 20")
    private String name;

    @Size(max = 255, message = "maximum size for description is 255")
    private String description;
}
