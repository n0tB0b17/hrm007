package com._7.hr.dto.role;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class RoleResponse {
    private String tenantId;
    private String roleId;
    private String name;
    private String description;
    private Set<String> permissions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
