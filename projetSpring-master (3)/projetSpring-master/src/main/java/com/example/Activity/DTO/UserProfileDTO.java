package com.esprit.project.DTO;

import lombok.Data;

@Data
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String email;
    private String profilePictureUrl;
}
