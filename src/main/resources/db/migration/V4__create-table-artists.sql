CREATE TABLE artists (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE tracks_artists (
    tracks_id BIGINT NOT NULL,
    artists_id BIGINT NOT NULL,
    PRIMARY KEY (tracks_id, artists_id),
    FOREIGN KEY (tracks_id) REFERENCES tracks (id) ON DELETE CASCADE,
    FOREIGN KEY (artists_id) REFERENCES artists (id) ON DELETE CASCADE
);