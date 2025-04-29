package com.example.Activity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



/**
 * DTO for representing a post in API responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String authorUsername; // Display username instead of full user object
    private Long authorId;
    private int upvoteCount;
    private int downvoteCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // We might add comment count later
}
