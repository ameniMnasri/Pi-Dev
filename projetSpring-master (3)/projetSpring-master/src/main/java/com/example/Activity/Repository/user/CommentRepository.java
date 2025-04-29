package com.example.Activity.Repository.user;


import com.example.Activity.entity.user.Comment;
import com.example.Activity.entity.user.Post;
import com.example.Activity.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Finds all comments belonging to a specific post.
     * Orders them by creation date ascending (oldest first) by default.
     *
     * @param post The post to retrieve comments for.
     * @return A list of comments for the specified post.
     */
    List<Comment> findByPost(Post post);

    /**
     * Finds all top-level comments for a specific post (comments with no parent comment).
     * Orders them by creation date ascending. Useful for starting the comment thread display.
     *
     * @param post The post to retrieve top-level comments for.
     * @return A list of top-level comments for the specified post.
     */
    List<Comment> findByPostAndParentCommentIsNullOrderByCreatedAtAsc(Post post);

    /**
     * Finds all top-level comments for a specific post (comments with no parent comment).
     *
     * @param postId The post ID.
     * @return A list of top-level comments for the specified post.
     */
    List<Comment> findByPostIdAndParentCommentIsNull(Long postId);


    /**
     * Finds all direct replies to a specific parent comment.
     * Orders them by creation date ascending. Useful for displaying nested replies.
     *
     * @param parentComment The parent comment to find replies for.
     * @return A list of replies for the specified parent comment.
     */
    List<Comment> findByParentCommentOrderByCreatedAtAsc(Comment parentComment);


    /**
     * Finds all comments made by a specific user.
     *
     * @param author The user who authored the comments.
     * @return A list of comments by the specified author.
     */
    List<Comment> findByAuthor(User author);

    // JpaRepository provides standard CRUD methods.
    // Add more custom queries if needed.
}
