package br.com.cotta.SpotHub.repository;

import br.com.cotta.SpotHub.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Integer> {
}
