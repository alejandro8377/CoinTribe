package com.cointribe.backend.controllers;

import com.cointribe.backend.models.Post;
import com.cointribe.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable Long id) {
        return postService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Map<String, String> body) {
        Post post = postService.createPost(
                body.get("autor"),
                body.get("etiqueta"),
                body.get("titulo"),
                body.get("cuerpo")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Post> like(@PathVariable Long id,
                                     @RequestBody Map<String, Long> body) {
        Post post = postService.likePost(id, body.get("userId"));
        return ResponseEntity.ok(post);
    }
}
