CREATE TABLE tracks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    artist VARCHAR(255),
    album VARCHAR(255),
    duration INT
);

CREATE TABLE lyrics (
    id SERIAL PRIMARY KEY,
    track_id BIGINT REFERENCES tracks(id),
    language VARCHAR(10),
    line TEXT,
    translated_line TEXT,
    timestamp INT
);





