package com._7.hr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.employee.Employee;

@Repository
public interface EmployeeRepository extends Neo4jRepository<Employee, Long> {
        String EMPLOYEE_CORE_MATCH = "MATCH (e: Employee {employeeId: $employeeId})-[ref0:WORKS_FOR]->(t: Tenant {tenantId: $tenantId})";
        String EMPLOYEE_ALL_MATCH = "MATCH (e: Employee)-[ref0:WORKS_FOR]->(t: Tenant {tenantId: $tenantId})";
        String EMPLOYEE_POSITION_OPTIONAL_MATCH = "OPTIONAL MATCH (e)-[ref1:HOLD_POSITION]->(p: Position) "
                        +
                        "OPTIONAL MATCH (p)-[ref2:HAS_ROLE]->(r: Role) "
                        +
                        "OPTIONAL MATCH (p)-[ref3:PART_OF]->(d: Department)";

        @Query(EMPLOYEE_CORE_MATCH + " OPTIONAL MATCH (e)-[dRel:MEMBER_OF]->(d: Department) " +
                        "RETURN e, ref0, t, dRel, d")
        Optional<Employee> findByEmployeeIdAndTenantId(String employeeId, String tenantId);

        @Query("MATCH (e: Employee {email: $email})-[:WORKS_FOR]->(t:Tenant {tenantId: $tenantId}) RETURN COUNT(e) > 0")
        boolean existsByEmailAndTenantId(String email, String tenantId);

        @Query("MATCH (e: Employee {email: $email})-[:WORKS_FOR]->(t:Tenant {tenantId: $tenantId}) WHERE e.employeeId <> $employeeIdToExclude RETURN count(e) > 0")
        boolean existsByEmailAndTenantIdAndEmployeeIdNot(String email, String tenantId, String employeeIdToExclude);

        @Query("MATCH (e: Employee {employeeId: $employeeId})-[:WORKS_FOR]->(t:Tenant {tenantId: $tenantId}) DETACH DELETE e")
        void deleteByEmployeeIdAndTenantId(String employeeId, String tenantId);

        @Query("MATCH (e: Employee {employeeId: $employeeId})-[:WORKS_FOR]->(t: Tenant {tenantId: $tenantId}) " +
                        "SET e.firstName = $firstName, " +
                        "e.lastName = $lastName, " +
                        "e.email = $email, " +
                        "e.jobTitle = $jobTitle, " +
                        "e.updatedAt = datetime() " +
                        "RETURN e, COLLECT(t)")
        Employee updateByEmployeeIdAndTenantId(String employeeId, String tenantId, String email, String jobTitle);

        @Query(EMPLOYEE_ALL_MATCH + EMPLOYEE_POSITION_OPTIONAL_MATCH + "RETURN e,ref0,t,ref1,p,ref2,r,ref3,d")
        List<Employee> findByTenantId(String tenantId);

        @Query("MATCH (t: Tenant {tenantId: $tenantId})<-[ref0:ASSOCIATED_WITH]-(p: Position)-[ref1:PART_OF]->(d: Department {departmentId: $departmentId}), "
                        +
                        "(e: Employee)-[ref2:HOLD_POSITION]->(p: Position), (e: Employee)-[ref3:WORKS_FOR]->(t: Tenant) "
                        +
                        "OPTIONAL MATCH (p: Position)-[ref4:HAS_ROLE]->(r: Role) " +
                        "RETURN t,ref0,p,ref1,d,ref2,e,ref4,r")
        List<Employee> findEmployeeByTenantIdAndDepartmentId(String tenantId, String departmentId);

        @Query("MATCH (e: Employee {employeeId: $employeeId})-[ref0:HOLD_POSITION]->(p) RETURN count(e) > 0")
        boolean isAssignedToPosition(String employeeId);

        @Query("MATCH (e: Employee)-[ref0:HOLD_POSITION]->(p: Position {positionId: $positionId}) WHERE (p)-[ref1:ASSOCIATED_WITH]->(t: Tenant {tenantId: $tenantId}) "
                        +
                        "RETURN e,ref0,p,ref1,t")
        Optional<Employee> findEmployeeByTenantIdAndPositionId(String tenantId, String positionId);
}
