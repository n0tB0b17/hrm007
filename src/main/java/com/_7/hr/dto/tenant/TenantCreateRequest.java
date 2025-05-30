package com._7.hr.dto.tenant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TenantCreateRequest {
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

    @NotBlank(message = "username cannot be blanked")
    @Size(min = 3, max = 10, message = "username should be minimum of 3 and maximum of 10")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "username should contain a-z,A-Z,0-9 and underscore,dot or hyphens")
    private String adminUserName;

    @NotBlank(message = "")
    @Size(min = 5, max = 20, message = "password should be minimum of 5 and maximum of 20")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "password should contain a-z,A-Z,0-9 and underscore,dot or hyphens")
    private String adminPassword;

    @NotBlank(message = "company logo cannot be blanked")
    private String logoURL;

    @NotBlank(message = "company primary color cannot be blanked")
    private String primaryColor;

    @NotBlank(message = "company primary color cannot be blanked")
    private String secondaryColor;
}