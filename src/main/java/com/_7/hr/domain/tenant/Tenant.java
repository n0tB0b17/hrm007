package com._7.hr.domain.tenant;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("tenant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
    @Id
    @GeneratedValue
    private Long id;

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
}