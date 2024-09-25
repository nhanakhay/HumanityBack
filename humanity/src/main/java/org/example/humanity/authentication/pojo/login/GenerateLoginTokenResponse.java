package org.example.humanity.authentication.pojo.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerateLoginTokenResponse {
    private String id;
    private String token;
}
