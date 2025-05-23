package com._7.hr.domain.tenant;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import lombok.Data;
import lombok.NoArgsConstructor;

@Node("Tenant")
@Data
@NoArgsConstructor
public class Tenant {
    @Id
    @GeneratedValue
    private Long elementId;

    @Property("tenantId")
    private String tenantId;

    @Property("companyName")
    private String companyName;

    @Property("companyType")
    private String companyType;

    @Property("companyContactEmail")
    private String companyContactEmail;

    @Property("companyContactNumber")
    private String companyContactNumber;

    @Property("status")
    private String status;

    // branding information
    @Property("logoURL")
    private String logoURL;

    @Property("primaryColor")
    private String primaryColor;

    @Property("secondaryColor")
    private String secondaryColor;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Tenant(String companyName, String companyType, String companyContactEmail, String companyContactNumber,
            String status, String logoURL, String primaryColor, String secondaryColor) {
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyContactEmail = companyContactEmail;
        this.companyContactNumber = companyContactNumber;
        this.status = status;
        this.logoURL = logoURL;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
}