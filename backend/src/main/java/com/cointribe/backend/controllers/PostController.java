package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.LikePostRequest;
import com.cointribe.backend.dto.PostRequest;
import com.cointribe.backend.dto.PostResponseDto;
import com.cointribe.backend.models.Post;
import com.cointribe.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<PostResponseDto>> getAll() {
        return ResponseEntity.ok(postService.getAllPosts().stream()
                .map(PostResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getById(@PathVariable Long id) {
        return postService.getById(id)
                .map(PostResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@RequestBody PostRequest body) {
        Post post = postService.createPost(
                body.autor(),
                body.etiqueta(),
                body.titulo(),
                body.cuerpo()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponseDto.from(post));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<PostResponseDto> like(@PathVariable Long id,
                                                @RequestBody LikePostRequest body) {
        Post post = postService.likePost(id, body.userId());
        return ResponseEntity.ok(PostResponseDto.from(post));
    }

    /** PUT /api/posts/{id} — Actualizar publicación */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable Long id,
                                                  @RequestBody PostRequest body) {
        Post post = postService.updatePost(
                id,
                body.autor(),
                body.etiqueta(),
                body.titulo(),
                body.cuerpo()
        );
        return ResponseEntity.ok(PostResponseDto.from(post));
    }

    /** DELETE /api/posts/{id} — Eliminar publicación */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
