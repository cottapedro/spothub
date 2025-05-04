CREATE TABLE auth_token (
    id BIGSERIAL PRIMARY KEY,
    access_token VARCHAR(255),
    token_type VARCHAR(255),
    expires_in INTEGER,
    refresh_token VARCHAR(255),
    scope VARCHAR(255),
    data_atualizacao TIMESTAMP
);