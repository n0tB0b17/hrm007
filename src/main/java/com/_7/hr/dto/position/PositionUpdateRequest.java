package com._7.hr.dto.position;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PositionUpdateRequest {
    @Size(min = 2, max = 20, message = "position name should be minimum of 2 and maximum of 20")
    private String name;

    @Size(max = 255, message = "position description maximum size is 255")
    private String description;

    private String departmentId;
    private Boolean isOpen;
}
