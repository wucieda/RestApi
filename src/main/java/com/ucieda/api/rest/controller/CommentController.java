package com.ucieda.api.rest.controller;

import com.ucieda.api.rest.entity.Comment;
import com.ucieda.api.rest.exception.ResourceNotFoundException;
import com.ucieda.api.rest.repository.CommentRepository;
import com.ucieda.api.rest.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post/{id}/comment")
    public Page<Comment> getCommentByPostId(@PathVariable Integer id, Pageable pageable) {
        return commentRepository.findByPostId(id, pageable);
    }

    @PostMapping("post/{id}/comment")
    public Comment saveComment(@PathVariable Integer id,
                               @Valid @RequestBody Comment comment) {
        return postRepository.findById(id).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("post with id: " + id + "not found"));
    }

    @PutMapping("post/{id}/comment/{commentId}")
    public Comment updateComment(@PathVariable Integer id,
                                 @PathVariable Integer commentId,
                                 @Valid @RequestBody Comment commentRequest) {

        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("post with id: " + id + "not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("comment with id: " + commentId + "not found"));
    }

    @DeleteMapping("post/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id,
                                           @PathVariable Integer commentId) {
        return commentRepository.findByIdAndPostId(commentId, id).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("comment with id: " + commentId + "not found" + "and post with id: " + id + "not found"));

    }

}
