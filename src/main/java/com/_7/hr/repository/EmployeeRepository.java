package com._7.hr.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.employee.Employee;

@Repository
public interface EmployeeRepository extends Neo4jRepository<Employee, Long> {
}
