package br.com.mcp.mcp_server.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.mcp.mcp_server.domain.model.Usuario;
import br.com.mcp.mcp_server.shared.dto.UsuarioFiltroDto;

public interface UsuarioService {

    public Page<Usuario> search(UsuarioFiltroDto filter, Pageable pageable);

    public Usuario findById(Long id);

    public Usuario create(Usuario usuario);

    public Usuario update(Long id, Usuario usuario);

    public void delete(Long id);
    
}
