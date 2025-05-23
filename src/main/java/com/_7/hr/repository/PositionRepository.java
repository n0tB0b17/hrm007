package com._7.hr.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.role.Role;

@Repository
public interface PositionRepository extends Neo4jRepository<Role, Long> {
}
