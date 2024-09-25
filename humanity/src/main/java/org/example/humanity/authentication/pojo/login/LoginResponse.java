package org.example.humanity.authentication.pojo.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String id;
    private String loginToken;
    private String refreshToken;
}
