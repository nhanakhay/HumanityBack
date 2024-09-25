package org.example.humanity.authentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.humanity.userFunctionality.profile.entity.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private String token;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = true)
    private Profile profile;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Modify as per your requirement
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Modify as per your requirement
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modify as per your requirement
    }


    @Override
    public boolean isEnabled() {
        return true;
    }



}
