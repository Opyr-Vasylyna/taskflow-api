
package com.vasylyna.taskflowapi.dto;

public class AuthResponse {

    private String token;
    private String type;

    public AuthResponse(String token) {
        this.token = token;
        this.type = "Bearer";
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
