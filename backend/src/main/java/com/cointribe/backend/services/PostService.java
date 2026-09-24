package com.cointribe.backend.services;

import com.cointribe.backend.exceptions.BusinessException;
import com.cointribe.backend.models.Post;
import com.cointribe.backend.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PostService {

    private final PostRepository postRepository;

    /**
     * Almacén en memoria de likes por usuario y post.
     * Clave: "userId:postId". En un sistema real iría en tabla de base de datos.
     */
    private final Set<String> likesRegistrados = ConcurrentHashMap.newKeySet();

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getById(Long id) {
        return postRepository.findById(id);
    }

    /**
     * Validaciones al crear una publicación.
     */
    @Transactional
    public Post createPost(String autor, String etiqueta, String titulo, String cuerpo) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new BusinessException("El autor de la publicación es obligatorio.");
        }
        if (titulo == null || titulo.trim().length() < 5) {
            throw new BusinessException("El título debe tener al menos 5 caracteres.");
        }
        if (cuerpo == null || cuerpo.trim().length() < 10) {
            throw new BusinessException("El contenido de la publicación debe tener al menos 10 caracteres.");
        }

        Post post = new Post();
        post.setAutor(autor.trim());
        post.setEtiqueta(etiqueta != null ? etiqueta.trim() : "General");
        post.setTitulo(titulo.trim());
        post.setCuerpo(cuerpo.trim());
        post.setLikes(0);
        return postRepository.save(post);
    }

    /**
     * REGLA DE NEGOCIO 6: Un usuario solo puede dar "me gusta" una vez
     * a la misma publicación. Intentos posteriores se rechazan.
     */
    @Transactional
    public Post likePost(Long postId, Long userId) {
        if (userId == null) {
            throw new BusinessException("Debes estar autenticado para dar me gusta.");
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("Publicación no encontrada."));

        String key = userId + ":" + postId;
        if (likesRegistrados.contains(key)) {
            throw new BusinessException("Ya diste me gusta a esta publicación. Solo se permite un like por usuario.");
        }

        likesRegistrados.add(key);
        post.setLikes(post.getLikes() + 1);
        return postRepository.save(post);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }
}
