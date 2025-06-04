package br.com.mcp.mcp_server.domain.repository;

import br.com.mcp.mcp_server.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
    
}
