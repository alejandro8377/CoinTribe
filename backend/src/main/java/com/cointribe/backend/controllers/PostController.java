package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.LikePostRequest;
import com.cointribe.backend.dto.PostCreateRequest;
import com.cointribe.backend.dto.PostRequest;
import com.cointribe.backend.dto.PostResponseDto;
import com.cointribe.backend.models.Post;
import com.cointribe.backend.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
@Tag(name = "Publicaciones", description = "Operaciones para consultar y administrar publicaciones")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(summary = "Listar publicaciones")
    @ApiResponse(responseCode = "200", description = "Lista de publicaciones")
    public ResponseEntity<List<PostResponseDto>> getAll() {
        return ResponseEntity.ok(postService.getAllPosts().stream()
                .map(PostResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener publicación")
    @ApiResponse(responseCode = "200", description = "Publicación encontrada")
    public ResponseEntity<PostResponseDto> getById(@PathVariable Long id) {
        return postService.getById(id)
                .map(PostResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear publicación")
    @ApiResponse(responseCode = "201", description = "Publicación creada")
    public ResponseEntity<PostResponseDto> create(@RequestBody @Valid PostCreateRequest body) {
        Post post = postService.createPost(
                body.autor(),
                body.etiqueta(),
                body.titulo(),
                body.cuerpo()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponseDto.from(post));
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "Dar me gusta a una publicación")
    @ApiResponse(responseCode = "200", description = "Me gusta registrado")
    public ResponseEntity<PostResponseDto> like(@PathVariable Long id,
            @RequestBody @Valid LikePostRequest body) {
        Post post = postService.likePost(id, body.userId());
        return ResponseEntity.ok(PostResponseDto.from(post));
    }

    /** PUT /api/posts/{id} — Actualizar publicación */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar publicación")
    @ApiResponse(responseCode = "200", description = "Publicación actualizada")
    public ResponseEntity<PostResponseDto> update(@PathVariable Long id,
            @RequestBody @Valid PostRequest body) {
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
    @Operation(summary = "Eliminar publicación")
    @ApiResponse(responseCode = "204", description = "Publicación eliminada")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
