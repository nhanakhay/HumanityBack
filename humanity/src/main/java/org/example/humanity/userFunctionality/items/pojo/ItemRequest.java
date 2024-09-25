package org.example.humanity.userFunctionality.items.pojo;

import lombok.Builder;
import lombok.Data;
import org.example.humanity.userFunctionality.items.entities.ItemCategory;
import org.example.humanity.userFunctionality.items.entities.YearsUsed;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ItemRequest {
    private String name;
//    @Getter
    private ItemCategory category;
    private String description;
    private String phone;
    private String email;
    private String location;
//    @Getter
    private YearsUsed yearsUsed;
    private String userName;
    private MultipartFile picture;

}
