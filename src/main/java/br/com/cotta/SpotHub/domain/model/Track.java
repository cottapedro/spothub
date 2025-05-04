package br.com.cotta.SpotHub.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tracks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Ou GenerationType.AUTO
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tracks_artists",
            joinColumns = @JoinColumn(name = "tracks_id"),
            inverseJoinColumns = @JoinColumn(name = "artists_id")
    )
    private Set<Artists> artists = new HashSet<>();

    private String album;

    @Column(nullable = false)
    private int duration;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lyrics> lyrics = new ArrayList<>();
}
