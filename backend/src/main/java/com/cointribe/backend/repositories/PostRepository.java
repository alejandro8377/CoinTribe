package com.cointribe.backend.repositories;

import com.cointribe.backend.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByEtiquetaIgnoreCase(String etiqueta);
    List<Post> findByTituloContainingIgnoreCase(String titulo);
    List<Post> findByAutorContainingIgnoreCaseOrderByFechaDesc(String autor);
}
