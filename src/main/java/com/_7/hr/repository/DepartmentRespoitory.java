package com._7.hr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.department.Department;

@Repository
public interface DepartmentRespoitory extends Neo4jRepository<Department, Long> {
    @Query("MATCH (d: Department {departmentId: $departmentId})-[:BELONGS_TO]->(t: Tenant {tenantId: $tenantId}) RETURN d, COLLECT(t) AS tenants")
    Optional<Department> findByDepartmentIdAndTenantId(String departmentId, String tenantId);

    @Query("MATCH (d: Department)-[:BELONGS_TO]->(t: Tenant {tenantId: $tenantId}) RETURN d, COLLECT(t)")
    List<Department> findAllByTenantId(String tenantId);

    @Query("MATCH (d: Department {name: $name})-[:BELONGS_TO]->(t: Tenant {tenantId: $tenantId}) RETURN COUNT(d) > 0")
    boolean existsByNameAndTenantId(String name, String tenantId);

    @Query("MATCH (d: Department {departmentId: $departmentId})-[:BELONGS_TO]->(t: Tenant {tenantId: $tenantId}) DETACH DELETE d RETURN COUNT(d) AS deletedCount")
    Long deleteByDepartmentIdAndTenantId(String departmentId, String tenantId);
}
