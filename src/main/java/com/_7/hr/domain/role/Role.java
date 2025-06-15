package com._7.hr.domain.role;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

@Node("Role")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private Long elementId;

    @Property("roleId")
    private String roleId;

    @Property("name")
    private String name;

    @Property("description")
    private String description;

    @Property("permission")
    private Set<String> permissions = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Relationship(type = "DEFINED_FOR", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tenant tenant;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
