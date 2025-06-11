package com._7.hr.security;

import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com._7.hr.domain.tenant.Tenant;
import com._7.hr.repository.TenantRepository;

public class TenantAdminUserDetailService implements UserDetailsService {
    private final TenantRepository tenantRepository;

    public TenantAdminUserDetailService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] parts = username.split("/", 2);
        if (parts.length != 2) {
            throw new UsernameNotFoundException("Username should be in: tenantId/username format");
        }

        String tenantId = parts[0];
        String userName = parts[1].toLowerCase();

        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new UsernameNotFoundException("Tenant not found for given tenantId"));

        if (tenant.getAdminUserName() != null && tenant.getAdminUserName().equalsIgnoreCase(userName)) {
            Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_TENANT_ADMIN"));
            return new User(
                    tenantId + "/" + tenant.getAdminUserName(),
                    tenant.getAdminPassword(),
                    authorities);
        } else {
            throw new UsernameNotFoundException("Username not found in given tenantId");
        }
    }
}
