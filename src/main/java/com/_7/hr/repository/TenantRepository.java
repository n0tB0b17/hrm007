package com._7.hr.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.tenant.Tenant;

@Repository
public interface TenantRepository extends Neo4jRepository<Tenant, Long> {
    Optional<Tenant> findByTenantId(String tenantId);

    boolean existsByCompanyName(String companyName);

    @Query("MATCH (t: Tenant) WHERE t.companyName = $companyName AND t.tenantId <> $tenantId RETURN count(t) > 0")
    boolean existsByCompanyNameAndTenantIdNot(String companyName, String tenantId);

    Long deleteByTenantId(String tenantId);
}
