package org.example.humanity.authentication.pojo.registeration;

import lombok.Builder;
import lombok.Data;
import org.example.humanity.adminFunctionality.pojo.UserResponse;
import org.example.humanity.authentication.entity.Role;
import org.example.humanity.authentication.entity.User;

@Data
@Builder
public class RegistrationResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private String userName;




    // Constructor should be public if accessed outside this class
    public RegistrationResponse(long id, String firstName, String lastName, String email, String phoneNumber, Role role, String userName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.userName = userName;

    }

    // This method maps an Items object to an ItemResponse
    public static RegistrationResponse mapToRegistrationResponse(User user) {
        return new RegistrationResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getUsername()

        );
    }

}
