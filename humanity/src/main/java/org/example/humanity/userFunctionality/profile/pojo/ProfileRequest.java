package org.example.humanity.userFunctionality.profile.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ProfileRequest {
    private String userName;
    private MultipartFile profileImage;
}
