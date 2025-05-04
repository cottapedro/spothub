package br.com.cotta.SpotHub.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_token")
@Getter
@Setter
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "token_type")
    private String tokenType;
    @Column(name = "expires_in")
    private Integer expiresIn;
    @Column(name = "refresh_token")
    private String refreshToken;
    private String scope;
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

}
