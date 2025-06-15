package com._7.hr.dto.auth;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String tokenType = "bearer";
    private String tenantId;
    private String accessToken;

    public JwtAuthenticationResponse(String tenantId, String accessToken) {
        this.tenantId = tenantId;
        this.accessToken = accessToken;
    }
}
