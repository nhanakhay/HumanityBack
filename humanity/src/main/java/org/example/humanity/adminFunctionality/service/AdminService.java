package org.example.humanity.adminFunctionality.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.humanity.adminFunctionality.pojo.UserResponse;
import org.example.humanity.authentication.entity.Role;
import org.example.humanity.authentication.entity.User;
import org.example.humanity.authentication.repo.AuthenticationRepo;
import org.example.humanity.common.exceptions.ResourceNotFoundException;
import org.example.humanity.userFunctionality.items.entities.Items;
import org.example.humanity.userFunctionality.items.pojo.ItemResponse;
import org.example.humanity.userFunctionality.items.repo.ItemRepository;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AuthenticationRepo Authenticationrepo;
    private final ItemRepository ItemRepository;

//    users

    public List<UserResponse> getAllUsers() {
        // Ensure your repository method returns a List<Items>
        List<User> user = Authenticationrepo.findAll();

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Items under the provided user name do not exist");
        }

        // Map each Items object to an ItemResponse using the static method
        return user.stream()
                .map(UserResponse::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) throws IOException {
        User user = Authenticationrepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with the provided id does not exist"));

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public UserResponse getUserByUsername(String username) throws IOException {
        User user = Authenticationrepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with the provided username does not exist"));

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public List<UserResponse> getUsersByRole(String role) throws IOException {
        Optional<User> users = Authenticationrepo.findByRole(Role.valueOf(role.toUpperCase()));

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("Users with the provided role do not exist");
        }

        return users.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstname())
                        .lastName(user.getLastname())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .role(user.getRole())
                        .build())
                .collect(Collectors.toList());
    }

    public String getNumberOfUsers() throws IOException {
        String numberOfUsers = String.valueOf(Authenticationrepo.totalNumberOfUsers());

        return "users : %s".formatted(numberOfUsers);

    }


    public String getNumberOfUsersByRole(String role) throws IOException {
        // Convert the role from String to Role enum
        Role roleEnum = Role.valueOf(role.toUpperCase());

        // Check if users with the provided role exist
        Authenticationrepo.findByRole(roleEnum)
                .orElseThrow(() -> new ResourceNotFoundException("Users with the provided role do not exist"));

        // Get the number of users with the specific role
        String numberOfRoleUsers = String.valueOf(Authenticationrepo.numberOfUsersWithASpecificRole(roleEnum));

        // Format and return the result
        return "%s users: %s".formatted(role, numberOfRoleUsers);
    }



    public List<ItemResponse> getAllItems() {
        // Ensure your repository method returns a List<Items>
        List<Items> items = ItemRepository.findAll();

        if (items.isEmpty()) {
            throw new ResourceNotFoundException("NO items found");
        }

        // Map each Items object to an ItemResponse using the static method
        return items.stream()
                .map(ItemResponse::mapToItemResponse)
                .collect(Collectors.toList());
    }


    public String getNumberOfItems() throws IOException {

        String numberOfRoleUsers = String.valueOf(ItemRepository.totalNumberOfItems());

        return "Total number of items : %s".formatted(numberOfRoleUsers);
    }


    public ItemResponse getAnItemByName(String name) throws IOException {
        Items item = ItemRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item with the provided name does not exist"));

        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .location(item.getLocation())
                .phone(item.getPhoneNumber())
                .category(String.valueOf(item.getCategory()))
                .email(item.getEmail())
                .yearsUsed(String.valueOf(item.getYearsUsed()))
                .picture(item.getImage())
                .username(item.getUsername())
                .build();
    }





}
