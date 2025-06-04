package br.com.mcp.mcp_server.application;

import br.com.mcp.mcp_server.domain.model.Usuario;
import br.com.mcp.mcp_server.domain.repository.UsuarioRepository;
import br.com.mcp.mcp_server.domain.service.UsuarioService;
import br.com.mcp.mcp_server.infrastructure.exception.EmailJaExisteException;
import br.com.mcp.mcp_server.infrastructure.exception.UsuarioNotFoundException;
import br.com.mcp.mcp_server.infrastructure.repository.UsuarioSpecs;
import br.com.mcp.mcp_server.shared.dto.UsuarioFiltroDto;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public Page<Usuario> search(UsuarioFiltroDto filter, Pageable pageable) {
        Specification<Usuario> spec = UsuarioSpecs.search(filter);

        return usuarioRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com ID: " + id));
    }

    @Transactional
    public Usuario create(Usuario usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new EmailJaExisteException("Email já está em uso: " + usuarioDTO.getEmail());
        }

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update(Long id, Usuario usuarioDTO) {
        Usuario usuario = findById(id);

        if (!usuario.getEmail().equals(usuarioDTO.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new EmailJaExisteException("Email já está em uso: " + usuarioDTO.getEmail());
        }

        BeanUtils.copyProperties(usuarioDTO, usuario, "id", "dataCriacao", "senha", "dataAtualizacao");

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
    }

}