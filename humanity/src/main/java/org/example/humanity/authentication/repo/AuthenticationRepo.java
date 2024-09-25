package org.example.humanity.authentication.repo;

import jakarta.transaction.Transactional;
import org.example.humanity.authentication.entity.User;
import org.example.humanity.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthenticationRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPhoneNumber(String email, String phoneNumber);
    Optional<User> findById(Long id);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByUsername(String userName);
    Optional<User> findByRole(Role role);
    List<User> findAll();


    @Modifying
    @Transactional
    @Query("UPDATE User o SET o.token = null WHERE o.token = :token")
    void clearOtpByOtp(String token);


//    @Modifying
    @Transactional
    @Query("SELECT COUNT(o) FROM User o")
    long totalNumberOfUsers();

    @Transactional
    @Query("SELECT COUNT(o) FROM User o WHERE o.role = :role")
    long numberOfUsersWithASpecificRole(Role role);

//
//    @Modifying
//    @Transactional
//    @Query("UPDATE User o SET o.profile = NULL WHERE o.profile = :id")
//    void updateUser(Long id);
}
