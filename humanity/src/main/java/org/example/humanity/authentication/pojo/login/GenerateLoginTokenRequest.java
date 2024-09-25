package org.example.humanity.authentication.pojo.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerateLoginTokenRequest {
    private String email;
    private String password;
}
