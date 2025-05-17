package com._7.hr.dto.tenant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TenantCreateRequest{
    @NotBlank(message = "company name cannot be blanked")
    @Size(min = 2, max = 20, message = "company name should be minimum of 2 and max of 20")
    private String companyName;

    @NotBlank(message = "company type cannot be blanked")
    private String companyType;

    @NotBlank(message = "contact email cannot be blanked")
    @Email(message = "contact email should be valid")
    private String companyContactEmail;


    private String companyContactNumber;
    private String status;

    @NotBlank(message = "company logo cannot be blanked")
    private String logoURL;

    @NotBlank(message = "company primary color cannot be blanked")
    private String primaryColor;
    
    @NotBlank(message = "company primary color cannot be blanked")
    private String secondaryColor;
}