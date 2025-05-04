CREATE TABLE lyrics (
    id SERIAL PRIMARY KEY,
    track_id BIGINT REFERENCES tracks(id),
    language VARCHAR(10),
    line TEXT,
    translated_line TEXT,
    timestamp INT
);