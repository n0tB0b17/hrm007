package com._7.hr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.role.Role;

@Repository
public interface RoleRepository extends Neo4jRepository<Role, Long> {
    @Query("MATCH (r: Role {roleId: $roleId})-[rel:DEFINED_BY]->(t: Tenant {tenantId: $tenantId}) RETURN r,rel,t")
    Optional<Role> findByRoleIdAndTenantId(String roleId, String tenantId);

    @Query("MATCH (r: Role)-[rel:DEFINED_BY]->(t: Tenant {tenantId: $tenantId}) RETURN r, rel, t")
    List<Role> findAllByTenantId(String tenantId);

    @Query("MATCH (r: Role { name: $name })-[rel:DEFINED_BY]->(t: Tenant {tenantId: $tenantId})")
    boolean existsByNameAndTenantId(String name, String tenantId);
}
