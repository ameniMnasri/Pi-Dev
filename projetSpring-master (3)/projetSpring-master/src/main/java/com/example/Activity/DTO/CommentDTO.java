package com.esprit.project.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long authorId;
    private Long postId;
    private Long parentId; //  to handle nested comments
    private int upvoteCount;
    private int downvoteCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDTO> replies; // Add this line
}

