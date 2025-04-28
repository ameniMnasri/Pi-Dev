package com.esprit.project.services;

import com.esprit.project.DTO.PostCreateDTO;
import com.esprit.project.DTO.PostResponseDTO;
import com.esprit.project.DTO.PostUpdateDTO;
import com.esprit.project.entities.Post;
import com.esprit.project.entities.User;
import com.esprit.project.repositories.PostRepository;
import com.esprit.project.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing Posts.
 * Handles business logic related to post creation, retrieval, update, and deletion.
 */
@Service
@RequiredArgsConstructor // Lombok annotation for constructor injection
public class PostService { // Renamed from PostServiceImpl, no 'implements' clause

    private final PostRepository postRepository;
    private final UserRepository userRepository; // To fetch the author

    /**
     * Creates a new post.
     *
     * @param postCreateDTO DTO containing post details.
     * @param authentication The authentication object of the logged-in user.
     * @return DTO representation of the created post.
     */
    @Transactional
    public PostResponseDTO createPost(PostCreateDTO postCreateDTO, Authentication authentication) {
        // Get the currently logged-in user
        User author = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + authentication.getName()));

        // Create a new Post entity
        Post post = new Post();
        post.setTitle(postCreateDTO.getTitle());
        post.setContent(postCreateDTO.getContent());
        post.setAuthor(author);
        // vote counts default to 0, timestamps are set automatically

        // Save the post
        Post savedPost = postRepository.save(post);

        // Convert to DTO and return
        return mapPostToResponseDTO(savedPost);
    }

    /**
     * Retrieves a list of all posts.
     * Orders by creation date descending.
     * TODO: Add pagination support later.
     *
     * @return A list of PostResponseDTOs.
     */
    @Transactional(readOnly = true) // Read-only transaction for fetching data
    public List<PostResponseDTO> getAllPosts() {
        // Fetch posts, potentially order them (e.g., by creation date descending)
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        // Map to DTOs
        return posts.stream()
                .map(this::mapPostToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single post by its ID.
     *
     * @param postId The ID of the post to retrieve.
     * @return DTO representation of the post.
     * @throws jakarta.persistence.EntityNotFoundException if post not found.
     */
    @Transactional(readOnly = true)
    public PostResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        return mapPostToResponseDTO(post);
    }

    /**
     * Updates an existing post.
     * Checks for ownership or ADMIN role before updating.
     *
     * @param postId The ID of the post to update.
     * @param postUpdateDTO DTO containing updated post details.
     * @param authentication The authentication object of the logged-in user (for permission check).
     * @return DTO representation of the updated post.
     * @throws jakarta.persistence.EntityNotFoundException if post not found.
     * @throws org.springframework.security.access.AccessDeniedException if the user is not authorized.
     */
    @Transactional
    public PostResponseDTO updatePost(Long postId, PostUpdateDTO postUpdateDTO, Authentication authentication) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        // --- Authorization Check ---
        checkPostOwnership(post, authentication); // Ensure only author or admin can update

        // Update fields
        post.setTitle(postUpdateDTO.getTitle());
        post.setContent(postUpdateDTO.getContent());
        // updatedAt timestamp will be updated automatically by @UpdateTimestamp

        Post updatedPost = postRepository.save(post);
        return mapPostToResponseDTO(updatedPost);
    }

    /**
     * Deletes a post.
     * Checks for ownership or ADMIN role before deleting.
     *
     * @param postId The ID of the post to delete.
     * @param authentication The authentication object of the logged-in user (for permission check).
     * @throws jakarta.persistence.EntityNotFoundException if post not found.
     * @throws org.springframework.security.access.AccessDeniedException if the user is not authorized.
     */
    @Transactional
    public void deletePost(Long postId, Authentication authentication) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        // --- Authorization Check ---
        checkPostOwnership(post, authentication); // Ensure only author or admin can delete

        postRepository.delete(post);
    }

    // --- Helper Methods ---

    /**
     * Maps a Post entity to a PostResponseDTO.
     * @param post The Post entity.
     * @return The corresponding PostResponseDTO.
     */
    private PostResponseDTO mapPostToResponseDTO(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor() != null ? post.getAuthor().getUsername() : "Unknown", // Handle potential null author?
                post.getAuthor() != null ? post.getAuthor().getId() : null,
                post.getUpvoteCount(),
                post.getDownvoteCount(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    /**
     * Checks if the authenticated user is the author of the post or has an ADMIN role.
     * Throws AccessDeniedException if not authorized.
     * @param post The post being accessed.
     * @param authentication The current user's authentication.
     */
    private void checkPostOwnership(Post post, Authentication authentication) {
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        // Ensure author object is not null before accessing username
        boolean isAuthor = post.getAuthor() != null && post.getAuthor().getUsername().equals(currentUsername);

        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("User does not have permission to modify this post.");
        }
    }
}
