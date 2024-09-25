package org.example.humanity.adminFunctionality.pojo;

import lombok.Builder;
import lombok.Data;
import org.example.humanity.authentication.entity.Role;
import org.example.humanity.authentication.entity.User;
import org.example.humanity.userFunctionality.items.entities.ItemCategory;
import org.example.humanity.userFunctionality.items.entities.Items;
import org.example.humanity.userFunctionality.items.entities.YearsUsed;
import org.example.humanity.userFunctionality.items.pojo.ItemResponse;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;


    // Constructor should be public if accessed outside this class
    public UserResponse(long id, String firstName, String lastName, String email, String phoneNumber, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;

    }

    // This method maps an Items object to an ItemResponse
    public static UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()

        );
    }
}
