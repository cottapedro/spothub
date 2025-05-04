package br.com.cotta.SpotHub.infrastructure.repository;

import br.com.cotta.SpotHub.domain.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findFirstByOrderByDataAtualizacaoDesc();
}
