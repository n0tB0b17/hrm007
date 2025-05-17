package com._7.hr.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com._7.hr.domain.tenant.Tenant;

@Repository
public interface TenantRepository extends Neo4jRepository<Tenant, Long> {
    Optional<Tenant> findByTenantId(String tenantId);

    boolean existsByCompanyName(String companyName);
}
