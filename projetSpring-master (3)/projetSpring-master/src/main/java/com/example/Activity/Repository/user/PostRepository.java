package com.example.Activity.Repository.user;


import com.example.Activity.entity.user.Post;
import com.example.Activity.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Post entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Finds all posts created by a specific user.
     *
     * @param author The user who authored the posts.
     * @return A list of posts by the specified author.
     */
    List<Post> findByAuthor(User author);

    /**
     * Finds all posts ordered by creation date descending (newest first).
     * Useful for displaying a feed of recent posts.
     *
     * @return A list of all posts sorted by creation date descending.
     */
    List<Post> findAllByOrderByCreatedAtDesc();

    // JpaRepository provides standard CRUD methods like:
    // - save(Post post)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    // - etc.

    // You can add more custom query methods here as needed,
    // for example, searching posts by title containing a keyword:
    // List<Post> findByTitleContainingIgnoreCase(String keyword);
}
