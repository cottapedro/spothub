package br.com.cotta.SpotHub.infrastructure.repository;

import br.com.cotta.SpotHub.domain.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Integer> {
    @Query("SELECT t FROM Track t JOIN t.artists a " +
            "WHERE t.name = :name AND a.name IN :artistNames " +
            "GROUP BY t " +
            "HAVING COUNT(DISTINCT a.name) = :artistCount")
    Optional<Track> findByNameAndArtistNames(@Param("name") String name,
                                             @Param("artistNames") List<String> artistNames,
                                             @Param("artistCount") long artistCount);

}
