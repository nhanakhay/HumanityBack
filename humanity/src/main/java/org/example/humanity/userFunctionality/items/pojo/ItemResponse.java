package org.example.humanity.userFunctionality.items.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.humanity.userFunctionality.items.entities.ItemCategory;
import org.example.humanity.userFunctionality.items.entities.Items;
import org.example.humanity.userFunctionality.items.entities.YearsUsed;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private long id;
    private String name;
    private String category;
    private String description;
    private String phone;
    private String email;
    private String location;
    private String yearsUsed;
    private String picture;
    private String username;



    // Constructor should be public if accessed outside this class
    public ItemResponse(long id, String name, String description, ItemCategory category, YearsUsed yearsUsed, String email, String picture, String phone, String location, String username) {
        this.id = id;
        this.name = name;
        this.category = String.valueOf(category);
        this.email = email;
        this.yearsUsed = String.valueOf(yearsUsed);
        this.description = description;
        this.picture = picture;
        this.phone = phone;
        this.location = location;
        this.username = username;
    }

    // This method maps an Items object to an ItemResponse
    public static ItemResponse mapToItemResponse(Items item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getCategory(),
                item.getYearsUsed(),
                item.getEmail(),
                item.getImage(),         // Assuming image corresponds to picture
                item.getPhoneNumber(),
                item.getLocation(),
                item.getUsername()
        );
    }
}
