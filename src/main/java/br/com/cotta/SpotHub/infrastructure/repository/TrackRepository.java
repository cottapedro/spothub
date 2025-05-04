package br.com.cotta.SpotHub.infrastructure.repository;

import br.com.cotta.SpotHub.domain.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Integer> {
}
