package org.example.humanity.userFunctionality.profile.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.humanity.authentication.pojo.registeration.RegistrationRequest;
import org.example.humanity.authentication.pojo.registeration.RegistrationResponse;
import org.example.humanity.userFunctionality.profile.pojo.ProfileRequest;
import org.example.humanity.userFunctionality.profile.pojo.ProfileResponse;
import org.example.humanity.userFunctionality.profile.repo.ProfileRepository;
import org.example.humanity.userFunctionality.profile.service.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;



    @PostMapping("create_profile")
    public ProfileResponse create_Profile(@ModelAttribute ProfileRequest request) throws IOException {

        return profileService.createProfile(request);
    }



    @GetMapping("get_profile_by_id")
    public ProfileResponse getProfile(@PathParam("id") Long id) throws IOException {

        return profileService.getAProfile(id);
    }

    @PutMapping("update_profile_by_id")
    public ProfileResponse updateAProfile(@ModelAttribute ProfileRequest request, @PathParam("id") Long id) throws IOException {

        return profileService.updateAProfile(request,id);
    }




}
