package org.example.humanity.adminFunctionality.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.humanity.adminFunctionality.pojo.UserResponse;
import org.example.humanity.adminFunctionality.service.AdminService;
import org.example.humanity.authentication.entity.Role;
import org.example.humanity.authentication.pojo.registeration.RegistrationRequest;
import org.example.humanity.authentication.pojo.registeration.RegistrationResponse;
import org.example.humanity.authentication.services.AuthenticationService;
import org.example.humanity.userFunctionality.items.pojo.ItemResponse;
import org.example.humanity.userFunctionality.items.service.ItemServices;
import org.example.humanity.userFunctionality.profile.pojo.ProfileResponse;
import org.example.humanity.userFunctionality.profile.service.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAuthenticationController {

    private final AuthenticationService authenticationService;
    private final AdminService adminService;
    private final ItemServices itemServices;
    private final ProfileService profileService;

    @PostMapping("registerAdmin")
    public RegistrationResponse registerAdmin() {

        return authenticationService.registerAdmin();
    }


    @PostMapping("update_user_details_by_id")
    public RegistrationResponse updateUserDetails(@RequestBody RegistrationRequest request, @PathParam("id") Long id) throws IOException {

        return authenticationService.updateUserDetails(request, id);
    }

    @GetMapping("get_all_users")
    public List<UserResponse> getAllUsers() {

        return adminService.getAllUsers();
    }

    @GetMapping("get_user_details_by_id")
    public UserResponse getUserById(@PathParam("id") Long id) throws IOException {

        return adminService.getUserById(id);
    }


    @GetMapping("get_user_details_by_userName")
    public UserResponse getUserByName(@PathParam("userName") String userName) throws IOException {

        return adminService.getUserByUsername(userName);
    }

    @GetMapping("get_users_by_role")
    public List<UserResponse> getUserByRole(@PathParam("role") String role) throws IOException {

        return adminService.getUsersByRole(role);
    }

    @GetMapping("get_total_number_of_users")
    public String getNumberOfUsers() throws IOException {

        return adminService.getNumberOfUsers();
    }

    @GetMapping("get_number_of_users_by_role")
    public String getNumberOfUsersByRole(@PathParam("role") String role) throws IOException {

        return adminService.getNumberOfUsersByRole(role);
    }



}
