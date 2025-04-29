package com.example.Activity.controller.user;


import com.example.Activity.DTO.CommentCreateDTO;
import com.example.Activity.DTO.CommentDTO;
import com.example.Activity.DTO.CommentUpdateDTO;
import com.example.Activity.service.user.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(
            @Valid @RequestBody CommentCreateDTO commentCreateDTO,
            Authentication authentication) {
        String username = authentication.getName();
        CommentDTO createdComment = commentService.createComment(commentCreateDTO, username);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateDTO commentUpdateDTO,
            Authentication authentication) {
        String username = authentication.getName();
        CommentDTO updatedComment = commentService.updateComment(commentId, commentUpdateDTO, username);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            Authentication authentication) {
        String username = authentication.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }
}

