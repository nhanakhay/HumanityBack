package org.example.humanity.userFunctionality.profile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.humanity.authentication.repo.AuthenticationRepo;
import org.example.humanity.common.exceptions.ResourceAlreadyExistsException;
import org.example.humanity.userFunctionality.items.utils.file.FileProcessorUtil;
import org.example.humanity.common.exceptions.ResourceNotFoundException;
import org.example.humanity.userFunctionality.items.entities.Items;
import org.example.humanity.userFunctionality.items.pojo.ItemRequest;
import org.example.humanity.userFunctionality.profile.pojo.ProfileResponse;
import org.example.humanity.userFunctionality.profile.repo.ProfileRepository;
import org.example.humanity.userFunctionality.items.pojo.ItemResponse;
import org.example.humanity.userFunctionality.profile.entity.Profile;
import org.example.humanity.userFunctionality.profile.pojo.ProfileRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FileProcessorUtil fileProcessorUtil;
    private final AuthenticationRepo authenticationRepo;

    public ProfileResponse createProfile(ProfileRequest request) throws IOException {
        log.info("function accessed");

        Profile profile = null;

        boolean isProfilePresent = profileRepository.findByUsernameAndProfileImage(request.getUserName(), null).isPresent();
        log.info("isProfilePresent {}", isProfilePresent);
        if(!isProfilePresent) {
            log.info("first if");
            throw new ResourceAlreadyExistsException(format("Profile with username %s already exists", request.getUserName()));

        }

        if(isProfilePresent) {
            log.info("second if");
            Optional<Profile> optionalProfile = profileRepository.findByUsername(request.getUserName());
            if (optionalProfile.isPresent()) {
                profile = optionalProfile.get();
                profile.setProfileImage(String.valueOf(request.getProfileImage()));
                profileRepository.save(profile);
            }
        }
        
        if( request.getProfileImage() == null) {

            log.info("third if");
            var newProfile = Profile.builder()
                    .username(request.getUserName())
                    .profileImage(String.valueOf(request.getProfileImage()))
                    .build();

            profile = profileRepository.save(newProfile);
            log.info("Item created: {}", profile);
        }

        

        return ProfileResponse.builder()
                .id(profile.getId())
                .username(profile.getUsername())
                .profileImage(profile.getProfileImage())
                .build();


    }


    public List<ProfileResponse> getAllProfile() {

        List<Profile> profile = profileRepository.findAll();

        if (profile.isEmpty()) {
            throw new ResourceNotFoundException("There are no saved profiles");
        }

        // Map each Items object to an ItemResponse using the static method
        return profile.stream()
                .map(ProfileResponse::mapToProfileResponse)
                .collect(Collectors.toList());
    }




    public ProfileResponse getAProfile(Long id) throws IOException {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Profile with the provided id does not exist"));

        return ProfileResponse.builder()
                .id(profile.getId())
                .username(profile.getUsername())
                .profileImage(profile.getProfileImage())
                .build();
    }

    public ProfileResponse updateAProfile(ProfileRequest request, Long id) throws IOException {
        // Step 1: Retrieve the existing item
        Profile existingProfile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));

        // Step 2: Update the fields of the existing item with the values from the request
        if (request.getUserName() != null) {
            existingProfile.setUsername(request.getUserName());
        }

        if (request.getProfileImage() != null) {
            // Assuming you're handling the image as a MultipartFile
            String imagePath = fileProcessorUtil.processFileBeforeSave(request.getProfileImage(), request.getUserName()); // Method to save the image and return the path
            existingProfile.setProfileImage(imagePath);
        }

        // Step 3: Save the updated item back to the database
        Profile updatedItem = profileRepository.save(existingProfile);

        // Step 4: Map the updated item to an ItemResponse and return it
        return ProfileResponse.mapToProfileResponse(updatedItem);
    }



    public String deleteProfile(Long id) throws IOException {
        Profile userProfile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Profile with the provided id does not exist"));


        profileRepository.updateProfile(userProfile.getProfileImage());
        return "Profile deleted successfully";

    }

}
