package com.example.Activity.controller.user;


import com.example.Activity.DTO.PostCreateDTO;
import com.example.Activity.DTO.PostResponseDTO;
import com.example.Activity.DTO.PostUpdateDTO;
import com.example.Activity.service.user.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Posts.
 */
@RestController
@RequestMapping("/api/posts") // Base path for all post-related endpoints
@RequiredArgsConstructor // Lombok annotation for constructor injection
public class PostController {

    private final PostService postService;

    /**
     * POST /api/posts : Create a new post.
     * Requires authentication.
     *
     * @param postCreateDTO The DTO containing post details.
     * @param authentication The current user's authentication details.
     * @return ResponseEntity with the created PostResponseDTO (201 Created).
     */
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostCreateDTO postCreateDTO, Authentication authentication) {
        // Authentication object is automatically injected by Spring Security
        // if the endpoint is secured and the user is logged in.
        PostResponseDTO createdPost = postService.createPost(postCreateDTO, authentication);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * GET /api/posts : Get all posts.
     * Publicly accessible (usually).
     *
     * @return ResponseEntity with a list of PostResponseDTOs (200 OK).
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * GET /api/posts/{postId} : Get a single post by ID.
     * Publicly accessible (usually).
     *
     * @param postId The ID of the post to retrieve.
     * @return ResponseEntity with the PostResponseDTO (200 OK) or 404 Not Found.
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long postId) {
        // Service method handles EntityNotFoundException, which can be mapped
        // to 404 Not Found using a @ControllerAdvice exception handler later.
        PostResponseDTO post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    /**
     * PUT /api/posts/{postId} : Update an existing post.
     * Requires authentication and ownership/admin rights.
     *
     * @param postId The ID of the post to update.
     * @param postUpdateDTO The DTO containing updated details.
     * @param authentication The current user's authentication details.
     * @return ResponseEntity with the updated PostResponseDTO (200 OK) or 404/403.
     */
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long postId,
                                                      @Valid @RequestBody PostUpdateDTO postUpdateDTO,
                                                      Authentication authentication) {
        // Service method handles authorization and not found exceptions.
        PostResponseDTO updatedPost = postService.updatePost(postId, postUpdateDTO, authentication);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * DELETE /api/posts/{postId} : Delete a post by ID.
     * Requires authentication and ownership/admin rights.
     *
     * @param postId The ID of the post to delete.
     * @param authentication The current user's authentication details.
     * @return ResponseEntity with no content (204 No Content) or 404/403.
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, Authentication authentication) {
        // Service method handles authorization and not found exceptions.
        postService.deletePost(postId, authentication);
        return ResponseEntity.noContent().build(); // Standard practice for successful DELETE
    }
}
