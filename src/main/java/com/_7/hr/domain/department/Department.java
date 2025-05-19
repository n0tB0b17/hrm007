package com._7.hr.domain.department;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import com._7.hr.domain.tenant.Tenant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Node("Department")
@Data
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue
    private Long elementId;

    @Property("departmentId")
    private String departmentId;

    @Property("name")
    private String name;

    @Property("description")
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tenant tenant;

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
