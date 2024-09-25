package org.example.humanity.adminFunctionality.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.humanity.userFunctionality.profile.pojo.ProfileResponse;
import org.example.humanity.userFunctionality.profile.service.ProfileService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/profile")
@RequiredArgsConstructor
public class AdminProfileController {

    private final ProfileService profileService;

    @GetMapping("get_all_profile")
    public List<ProfileResponse> getAllItemsByUsername(){

        return profileService.getAllProfile();
    }


    @DeleteMapping("delete_profile")
    public String deleteProfile(@PathParam("id") Long id) throws IOException {

        return profileService.deleteProfile(id);
    }

}
