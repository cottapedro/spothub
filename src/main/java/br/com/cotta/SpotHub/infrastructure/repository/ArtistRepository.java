package br.com.cotta.SpotHub.infrastructure.repository;

import br.com.cotta.SpotHub.domain.model.Artists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artists, Long> {
    Optional<Artists> findByName(String name);
}
