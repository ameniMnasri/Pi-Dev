package com.esprit.project.repositories;

import com.esprit.project.entities.Comment;
import com.esprit.project.entities.Post;
import com.esprit.project.entities.User;
import com.esprit.project.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Vote entity.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * Finds a vote cast by a specific user on a specific post.
     * Useful for checking if a user has already voted on a post and what their vote was.
     *
     * @param user The user who cast the vote.
     * @param post The post that was voted on.
     * @return An Optional containing the Vote if found, otherwise empty.
     */
    Optional<Vote> findByUserAndPost(User user, Post post);

    /**
     * Finds a vote cast by a specific user on a specific comment.
     * Useful for checking if a user has already voted on a comment and what their vote was.
     *
     * @param user The user who cast the vote.
     * @param comment The comment that was voted on.
     * @return An Optional containing the Vote if found, otherwise empty.
     */
    Optional<Vote> findByUserAndComment(User user, Comment comment);

    // JpaRepository provides standard CRUD methods.
    // You might not need many custom methods here as votes are often
    // looked up in the context of a user and a post/comment.
}
