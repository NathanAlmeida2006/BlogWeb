package dev.nathan.web.repositories;

import dev.nathan.web.models.Comentario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, UUID> {
}
