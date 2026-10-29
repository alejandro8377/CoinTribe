package com.cointribe.backend.dto;

import com.cointribe.backend.models.Post;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String autor,
        String etiqueta,
        String titulo,
        String cuerpo,
        Integer likes,
        LocalDateTime fecha
) {

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getAutor(),
                post.getEtiqueta(),
                post.getTitulo(),
                post.getCuerpo(),
                post.getLikes(),
                post.getFecha()
        );
    }
}
