package com._7.hr.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tenant/{tenantId}/roles")
@Tag(name = "Roles", description = "role related APIs for tenant")
public class RoleController {

}
