package com._7.hr.dto.position;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PositionCreateRequest {
    @NotBlank(message = "position name cannot be blanked")
    @Size(min = 4, max = 20, message = "position name should be minimum of 4 and maximum of 20")
    private String name;

    @Size(max = 255, message = "position description maximum limit is 255")
    private String description;

    @NotNull(message = "roleId cannot be null")
    private String roleId;
    private String departmentId;

    private boolean isOpen = true;
}
