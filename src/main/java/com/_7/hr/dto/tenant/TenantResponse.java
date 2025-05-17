package com._7.hr.dto.tenant;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TenantResponse {
    private String tenantId;
    private String companyName;
    private String companyType;
    private String companyContactEmail;
    private String companyContactNumber;
    private String status;
    private String logoURL;
    private String primaryColor;
    private String secondaryColor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}