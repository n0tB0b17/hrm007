package com._7.hr.dto.role;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RoleResponse {
    private String tenantId;
    private String roleId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
