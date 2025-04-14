package dev.nathan.web.repositories;

import dev.nathan.web.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAll();

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comentarios WHERE p.id = :id")
    Optional<Post> findByIdWithComentarios(UUID id);
}