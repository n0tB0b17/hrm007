package com._7.hr.dto.tenant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TenantUpdateRequest {
    @Size(min = 2, max = 20, message = "company name should be greater than 2 and smaller than 20")
    private String companyName;
    private String companyType;
    @Email(message = "email should be from a valid service provider")
    private String companyContactEmail;
    private String companyContactNumber;
    private String status;
    private String logoURL;
    private String primaryColor;
    private String secondaryColor;
}