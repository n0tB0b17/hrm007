package com._7.hr.domain.position;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import com._7.hr.domain.department.Department;
import com._7.hr.domain.role.Role;
import com._7.hr.domain.tenant.Tenant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Node("Position")
@Data
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue
    private Long elementId;

    @Property("positionId")
    private String positionId;

    @Property("name")
    private String name;

    @Property("description")
    private String description;

    @Property("isOpen")
    private boolean isOpen = true;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Relationship(type = "HAS_ROLE", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Role role;

    @Relationship(type = "PART_OF", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Department department;

    @Relationship(type = "ASSOCIATED_WITH", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tenant tenant;

    public Position(String name, String description, boolean isOpen) {
        this.name = name;
        this.description = description;
        this.isOpen = isOpen;
    }
}
