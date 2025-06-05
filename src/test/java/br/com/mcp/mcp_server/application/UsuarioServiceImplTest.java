package br.com.mcp.mcp_server.application;

import br.com.mcp.mcp_server.domain.model.Usuario;
import br.com.mcp.mcp_server.domain.repository.UsuarioRepository;
import br.com.mcp.mcp_server.infrastructure.exception.EmailJaExisteException;
import br.com.mcp.mcp_server.infrastructure.exception.UsuarioNotFoundException;

import br.com.mcp.mcp_server.shared.dto.UsuarioFiltroDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveBuscarUsuariosComFiltroEPaginacao() {
        UsuarioFiltroDto filtro = new UsuarioFiltroDto();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Usuario> page = new PageImpl<>(List.of(new Usuario()));

        when(usuarioRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<Usuario> resultado = usuarioService.search(filtro, pageable);

        assertEquals(1, resultado.getTotalElements());
        verify(usuarioRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void deveRetornarUsuarioQuandoEncontradoPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findById(1L);

        assertEquals(1L, resultado.getId());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoPorId() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.findById(99L));
    }

    @Test
    void deveCriarUsuarioQuandoEmailNaoExiste() {
        Usuario usuarioDTO = new Usuario();
        usuarioDTO.setEmail("novo@email.com");

        when(usuarioRepository.existsByEmail("novo@email.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario criado = usuarioService.create(usuarioDTO);

        assertEquals("novo@email.com", criado.getEmail());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComEmailExistente() {
        Usuario usuarioDTO = new Usuario();
        usuarioDTO.setEmail("jaexiste@email.com");

        when(usuarioRepository.existsByEmail("jaexiste@email.com")).thenReturn(true);

        assertThrows(EmailJaExisteException.class, () -> usuarioService.create(usuarioDTO));
    }

    @Test
    void deveAtualizarUsuarioQuandoEmailNaoEstaEmUso() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("antigo@email.com");

        Usuario usuarioDTO = new Usuario();
        usuarioDTO.setEmail("novo@email.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.existsByEmail("novo@email.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario atualizado = usuarioService.update(1L, usuarioDTO);

        assertEquals("novo@email.com", atualizado.getEmail());
        verify(usuarioRepository).save(usuarioExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarUsuarioComEmailJaEmUso() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("antigo@email.com");

        Usuario usuarioDTO = new Usuario();
        usuarioDTO.setEmail("jaexiste@email.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.existsByEmail("jaexiste@email.com")).thenReturn(true);

        assertThrows(EmailJaExisteException.class, () -> usuarioService.update(1L, usuarioDTO));
    }

    @Test
    void deveDeletarUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.delete(1L);

        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.delete(2L));
    }
}