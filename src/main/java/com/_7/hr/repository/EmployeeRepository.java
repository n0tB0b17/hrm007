package com._7.hr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.employee.Employee;

@Repository
public interface EmployeeRepository extends Neo4jRepository<Employee, Long> {
    @Query("MATCH (e: Employee {employeeId: $employeeId})-[:WORKS_FOR]->(t:Tenant {tenantId: $tenantId}) RETURN e, COLLECT(t) AS tenants")
    Optional<Employee> findByEmployeeIdAndTenantId(String employeeId, String tenantId);

    @Query("MATCH (e: Employee)-[:WORKS_FOR]->(t: Tenant {tenantId: $tenantId}) RETURN e, COLLECT(t) AS tenants")
    List<Employee> findAllByTenantId(String tenantId);

    @Query("MATCH (e: Employee {email: $email})-[:WORKS_FOR]->(t:Tenant {tenantId: $tenantId}) RETURN COUNT(e) > 0")
    boolean existsByEmailAndTenantId(String email, String tenantId);

    @Query("MATCH (e: Employee {email: $email})-[:WORKS_FOR]->(t:Tenant {tenantId: $tenantId}) WHERE e.employeeId <> $employeeIdToExclude RETURN count(e) > 0")
    boolean existsByEmailAndTenantIdAndEmployeeIdNot(String email, String tenantId, String employeeIdToExclude);

    @Query("MATCH (e: Employee {employeeId: $employeeId})-[:WORKS_FOR]->(t:Tenant {tenantId: $tenantId}) DETACH DELETE e")
    void deleteByEmployeeIdAndTenantId(String employeeId, String tenantId);
}
