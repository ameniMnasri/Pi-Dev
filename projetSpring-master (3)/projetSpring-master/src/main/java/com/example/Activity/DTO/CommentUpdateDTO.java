package com.example.Activity.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CommentUpdateDTO {
    @NotBlank
    private String text;
}
