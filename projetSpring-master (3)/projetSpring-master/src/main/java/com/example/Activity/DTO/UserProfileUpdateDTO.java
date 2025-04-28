package com.esprit.project.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateDTO {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String profilePictureUrl;
}
