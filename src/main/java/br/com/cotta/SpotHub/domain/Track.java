package br.com.cotta.SpotHub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tracks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Ou GenerationType.AUTO
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String artist;

    private String album;

    @Column(nullable = false)
    private int duration;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lyrics> lyrics;
}
