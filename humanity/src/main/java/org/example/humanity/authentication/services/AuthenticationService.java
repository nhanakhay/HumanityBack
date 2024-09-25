package org.example.humanity.authentication.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.humanity.authentication.entity.Role;
import org.example.humanity.authentication.entity.User;
import org.example.humanity.authentication.pojo.login.GenerateLoginTokenRequest;
import org.example.humanity.authentication.pojo.login.GenerateLoginTokenResponse;
import org.example.humanity.authentication.pojo.login.LoginRequest;
import org.example.humanity.authentication.pojo.login.LoginResponse;
import org.example.humanity.authentication.config.JwtService;
import org.example.humanity.authentication.pojo.registeration.RegistrationResponse;
import org.example.humanity.authentication.pojo.registeration.RegistrationRequest;
import org.example.humanity.authentication.repo.AuthenticationRepo;
import org.example.humanity.authentication.utility.GenerateOTP;
import org.example.humanity.common.exceptions.ResourceAlreadyExistsException;
import org.example.humanity.common.exceptions.ResourceNotFoundException;
import org.example.humanity.userFunctionality.profile.entity.Profile;
import org.example.humanity.userFunctionality.profile.pojo.ProfileRequest;
import org.example.humanity.userFunctionality.profile.pojo.ProfileResponse;
import org.example.humanity.userFunctionality.profile.repo.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationRepo repo;
    private final GenerateOTP generateOTP;
    private final JwtService jwtService;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;


    public RegistrationResponse register(RegistrationRequest request) {
        boolean isUserPresent = repo.findByEmailAndPhoneNumber(request.getEmail(),request.getPhoneNumber()).isPresent();
        if(isUserPresent) {
            throw new ResourceAlreadyExistsException(format("User with email %s already exists", request.getEmail()));

        }
        // Create the profile
        var profile = Profile.builder()
                .username(request.getFirstName() +
                        (request.getLastName() != null && !request.getLastName().isEmpty() ? request.getLastName().charAt(0) : ""))
                .build();

        // Create the user
        var user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Encrypt the password
                .username(profile.getUsername()) // Use the profile's username
                .token("NotSet")
                .role(Role.USER)
                .profile(profile) // Associate the profile with the user
                .build();

        // Save the profile and user
        profileRepository.save(profile);
        User savedUser = repo.save(user);

        return RegistrationResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstname())
                .lastName(savedUser.getLastname())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .role(savedUser.getRole())
                .build();

    }


    public GenerateLoginTokenResponse generateLoginToken(GenerateLoginTokenRequest request) {
        // Find user by email first
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        // Validate the provided password against the stored encrypted password
        boolean isPasswordValid = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isPasswordValid) {
            throw new ResourceNotFoundException("Invalid password");
        }


        User users = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with the provided email does not exist"));

        String token = generateOTP.generateOTP(); // Implement this method as needed

        users.setToken(token);
        User savedUser = repo.save(users);

        return GenerateLoginTokenResponse.builder()
                .id(String.valueOf(savedUser.getId()))
                .token(savedUser.getToken())
                .build();




    }


    public LoginResponse loginUser(LoginRequest request) {

        User users = repo.findById(Long.valueOf(request.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with the provided id does not exist"));

        String token = generateOTP.verifyOTP(request.getId(), request.getToken()); // Implement this method as needed

        if (!token.equals("Otp Verified")){
            throw new ResourceNotFoundException("Wrong OTP verification code");
        }

        Map<String, String> response;
        response = getAuthToken(users);

        return LoginResponse.builder()
                .id(request.getId())
                .loginToken(response.get("newJwtToken"))
                .refreshToken(response.get("newRefresh"))
                .build();



    }




    public RegistrationResponse registerAdmin() {
        // Build the User object
        var user = User.builder()
                .phoneNumber("0204587679")
                .firstname("Kay")
                .lastname("Adu")
                .email("kayAdu@gmail.com")
                .password(passwordEncoder.encode("Kaykay")) // Encrypt the password
                .username("KayAdu") // Assuming a simple logic for username, you can adjust as needed
                .token("NotSet")
                .role(Role.ADMIN)
                .build();

        // Check if the user already exists
        boolean isUserPresent = repo.findByEmailAndPhoneNumber(user.getEmail(), user.getPhoneNumber()).isPresent();
        if (isUserPresent) {
            throw new ResourceAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
        }

        // Save the user to the repository
        User savedUser = repo.save(user);

        // Build and return the RegistrationResponse
        return RegistrationResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstname())
                .lastName(savedUser.getLastname())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .role(savedUser.getRole())
                .userName(savedUser.getUsername())
                .build();
    }




    public RegistrationResponse updateUserDetails(RegistrationRequest request, Long id) throws IOException {
        // Step 1: Retrieve the existing item
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Step 2: Update the fields of the existing item with the values from the request
        if (request.getFirstName() != null) {
            user.setFirstname(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastname(request.getLastName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        // Step 3: Save the updated item back to the database
        User updatedUser = repo.save(user);

        // Step 4: Map the updated item to an ItemResponse and return it
        return RegistrationResponse.mapToRegistrationResponse(updatedUser);
    }


    public Map<String, String> getAuthToken(User user) {
        Map<String, String> response = new HashMap<>();

        String newJwtToken = jwtService.generateToken((UserDetails) user);
        String newRefreshToken = jwtService.generateRefreshToken((UserDetails) user);

        response.put("newJwtToken", newJwtToken);
        response.put("newRefresh", newRefreshToken);

        return response;
    }

}
