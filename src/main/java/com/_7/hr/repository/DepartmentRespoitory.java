package com._7.hr.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.department.Department;

@Repository
public interface DepartmentRespoitory extends Neo4jRepository<Department, Long> {

}
