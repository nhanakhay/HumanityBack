package org.example.humanity.authentication.pojo.registeration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

}
