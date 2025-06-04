package br.com.mcp.mcp_server.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "des_nome", nullable = false)
    private String nome;

    @Column(name = "des_email", nullable = false, unique = true)
    private String email;

    @Column(name = "des_senha", nullable = false)
    private String senha;

    @Column(name = "des_perfil", nullable = false)
    private String perfil;

    @Column(name = "des_status", nullable = false)
    private String status;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

}
