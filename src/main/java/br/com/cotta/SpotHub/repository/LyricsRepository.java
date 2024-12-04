package br.com.cotta.SpotHub.repository;

import br.com.cotta.SpotHub.domain.Lyrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LyricsRepository extends JpaRepository<Lyrics,Integer> {
}
