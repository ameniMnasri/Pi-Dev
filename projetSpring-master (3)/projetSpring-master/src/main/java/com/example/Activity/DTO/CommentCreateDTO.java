package com.esprit.project.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CommentCreateDTO {
    @NotNull
    private Long postId;
    @NotBlank
    private String text;
    private Long parentId; //  to handle nested comments
}