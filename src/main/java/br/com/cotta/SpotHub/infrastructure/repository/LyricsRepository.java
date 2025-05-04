package br.com.cotta.SpotHub.infrastructure.repository;

import br.com.cotta.SpotHub.domain.model.Lyrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LyricsRepository extends JpaRepository<Lyrics,Integer> {
}
