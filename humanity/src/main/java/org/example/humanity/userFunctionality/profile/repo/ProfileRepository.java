package org.example.humanity.userFunctionality.profile.repo;

import jakarta.transaction.Transactional;
import org.example.humanity.userFunctionality.items.entities.Items;
import org.example.humanity.userFunctionality.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findById(Long id);
    Optional<Profile> findByUsernameAndProfileImage(String userName, String profileImage);
    Optional<Profile> findByUsername(String userName);
    List<Profile>  findAll();


    @Modifying
    @Transactional
    @Query("UPDATE Profile o SET o.profileImage = NULL WHERE o.profileImage = :image")
    void updateProfile(String image);

//    UPDATE Profile o SET o.profileImage = NULL WHERE o.profileImage = :image

}
