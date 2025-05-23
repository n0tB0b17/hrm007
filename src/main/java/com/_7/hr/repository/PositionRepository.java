package com._7.hr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.position.Position;

@Repository
public interface PositionRepository extends Neo4jRepository<Position, Long> {
    @Query("MATCH (p: Position)-[rel1:ASSOCIATED_WITH]->(t: Tenant {tenantId: $tenantId}) "
            +
            "MATCH (p)-[rel2:HAS_ROLE]->(r: Role) " +
            "OPTIONAL MATCH (p)-[rel3:PART_OF]->(d: Department) " +
            "RETURN p,rel1,t,rel2,r,rel3,d")
    List<Position> findAllByTenantId(String tenantId);

    @Query("MATCH (p: Position {positionId: $positionId})-[rel1:ASSOCIATED_WITH]->(t: Tenant {tenantId: $tenantId}) "
            +
            "MATCH (p)-[rel2:HAS_ROLE]->(r: Role) " +
            "OPTIONAL MATCH (p)-[rel3:PART_OF]->(d: Department) " +
            "RETURN p,rel1,t,rel2,r,rel3,d")
    Optional<Position> findByPositionIdAndTenantId(String positionId, String tenantId);

    @Query("MATCH (p: Position {name: $name})-[rel:ASSOCIATED_WITH]->(t: Tenant {tenantId: $tenantId}) RETURN COUNT(p) > 0")
    boolean existsByNameAndTenantId(String name, String tenantId);

    @Query("MATCH (p: Position {positionId: $positionId})-[rel:ASSOCIATED_WITH]->(t: Tenant {tenantId: $tenantId}) DETACH DELETE p RETURN COUNT(p) AS deletedCount")
    Long deleteByIdAndTenantId(String positionId, String tenantId);

}
