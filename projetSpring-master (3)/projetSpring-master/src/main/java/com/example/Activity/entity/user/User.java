package com.example.Activity.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(nullable = true)
    private String profilePictureUrl;

    @Column(nullable = true)
    private String first_name;

    @Column(nullable = true)
    private String last_name;

    @Column(nullable = true)
    private LocalDate birthdate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.name()); // Convert role to GrantedAuthority
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
