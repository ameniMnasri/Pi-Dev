package com.esprit.project.services;

import com.esprit.project.DTO.CommentCreateDTO;
import com.esprit.project.DTO.CommentDTO;
import com.esprit.project.DTO.CommentUpdateDTO;
import com.esprit.project.entities.Comment;
import com.esprit.project.entities.Post;
import com.esprit.project.entities.User;
import com.esprit.project.exceptions.ResourceNotFoundException;
import com.esprit.project.repositories.CommentRepository;
import com.esprit.project.repositories.PostRepository;
import com.esprit.project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentDTO createComment(CommentCreateDTO commentCreateDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(commentCreateDTO.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getText()); // 'text' from DTO
        comment.setAuthor(user);
        comment.setPost(post);
        if (commentCreateDTO.getParentId() != null) {
            Comment parentComment = commentRepository.findById(commentCreateDTO.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }

        comment = commentRepository.save(comment);
        return mapToDTO(comment);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdAndParentCommentIsNull(postId);
        return comments.stream()
                .map(this::mapToDTOWithReplies)
                .collect(Collectors.toList());
    }



    @Transactional
    public CommentDTO updateComment(Long commentId, CommentUpdateDTO commentUpdateDTO, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new SecurityException("You are not authorized to update this comment");
        }

        comment.setContent(commentUpdateDTO.getText());
        comment = commentRepository.save(comment);
        return mapToDTO(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new SecurityException("You are not authorized to delete this comment");
        }
        //delete the comment.  Replies should be deleted due to cascade.
        commentRepository.delete(comment);
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setAuthorUsername(comment.getAuthor().getUsername());
        commentDTO.setAuthorId(comment.getAuthor().getId());
        commentDTO.setPostId(comment.getPost().getId());
        commentDTO.setParentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null);
        commentDTO.setUpvoteCount(comment.getUpvoteCount());
        commentDTO.setDownvoteCount(comment.getDownvoteCount());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setUpdatedAt(comment.getUpdatedAt());
        return commentDTO;
    }
    private CommentDTO mapToDTOWithReplies(Comment comment) {
        CommentDTO commentDTO = mapToDTO(comment);
        List<Comment> replies = comment.getReplies();
        if (replies != null && !replies.isEmpty()) {
            List<CommentDTO> replyDTOs = replies.stream()
                    .map(this::mapToDTOWithReplies) // Recursive call to handle nested replies
                    .collect(Collectors.toList());
            commentDTO.setReplies(replyDTOs); // Add this line to set the replies to the DTO
        }
        return commentDTO;
    }
}

