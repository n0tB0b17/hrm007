package com._7.hr.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._7.hr.dto.role.RoleCreateRequest;
import com._7.hr.dto.role.RoleResponse;
import com._7.hr.dto.role.RoleUpdateRequest;
import com._7.hr.service.RoleService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tenant/{tenantId}/roles")
@Tag(name = "Roles APIs", description = "this contains all required APIs for tenant's role related services")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@PathVariable String tenantId,
            @RequestBody RoleCreateRequest roleCreateRequest) {
        RoleResponse roleResponse = roleService.createRole(tenantId, roleCreateRequest);
        return new ResponseEntity<>(roleResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles(@PathVariable String tenantId) {
        List<RoleResponse> roles = roleService.getRolesByTenantId(tenantId);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponse> getARoleById(@PathVariable String tenantId, @PathVariable String roleId) {
        RoleResponse role = roleService.getRoleByIdAndTenantId(tenantId, roleId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Object> deleteRoleById(@PathVariable String tenantId, @PathVariable String roleId) {
        boolean resp = roleService.deleteRoleByIdAndTenantId(tenantId, roleId);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponse> updateRoleById(@PathVariable String tenantId, @PathVariable String roleId,
            RoleUpdateRequest updateRequest) {
        return null;
    }
}
