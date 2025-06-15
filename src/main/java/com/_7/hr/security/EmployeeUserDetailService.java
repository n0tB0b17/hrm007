package com._7.hr.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._7.hr.repository.EmployeeRepository;

@Service("employeeUserDetailService")
public class EmployeeUserDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeUserDetailService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
