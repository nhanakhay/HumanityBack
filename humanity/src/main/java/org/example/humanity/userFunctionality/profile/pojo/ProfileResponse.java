package org.example.humanity.userFunctionality.profile.pojo;

import lombok.Builder;
import lombok.Data;
import org.example.humanity.userFunctionality.items.entities.ItemCategory;
import org.example.humanity.userFunctionality.items.entities.Items;
import org.example.humanity.userFunctionality.items.entities.YearsUsed;
import org.example.humanity.userFunctionality.items.pojo.ItemResponse;
import org.example.humanity.userFunctionality.profile.entity.Profile;

@Data
@Builder
public class ProfileResponse {

    private Long id;
    private String username;
    private String profileImage;


    // Constructor should be public if accessed outside this class
    public ProfileResponse(long id, String username, String profileImage) {
        this.id = id;
        this.username = username;
        this.profileImage = profileImage;

    }

    // This method maps an Items object to an ItemResponse
    public static ProfileResponse mapToProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getUsername(),
                profile.getProfileImage()

        );
    }
}
