package br.com.mcp.mcp_server.infrastructure.controller;

import br.com.mcp.mcp_server.domain.model.Usuario;
import br.com.mcp.mcp_server.domain.service.UsuarioService;
import br.com.mcp.mcp_server.shared.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("João");
        usuarioDTO.setEmail("joao@email.com");
    }

    @Test
    void deveConsultarUsuariosPorFiltros() throws Exception {
        Page<Usuario> usuariosPage = new PageImpl<>(Collections.singletonList(usuario));
        Page<UsuarioDTO> usuariosDtoPage = new PageImpl<>(Collections.singletonList(usuarioDTO));

        when(usuarioService.search(any(UsuarioFiltroDto.class), any(PageRequest.class))).thenReturn(usuariosPage);
        when(modelMapper.map(any(Usuario.class), eq(UsuarioDTO.class))).thenReturn(usuarioDTO);

        mockMvc.perform(get("/usuarios")
                .param("nome", "João")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("João"));
    }

    @Test
    void deveConsultarUsuarioPorId() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void deveCriarUsuario() throws Exception {
        UsuarioSaveDTO saveDTO = new UsuarioSaveDTO();
        saveDTO.setNome("João");
        saveDTO.setEmail("joao@email.com");

        when(modelMapper.map(any(UsuarioSaveDTO.class), eq(Usuario.class))).thenReturn(usuario);
        when(usuarioService.create(any(Usuario.class))).thenReturn(usuario);
        when(modelMapper.map(any(Usuario.class), eq(UsuarioDTO.class))).thenReturn(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        UsuarioUpdateDTO updateDTO = new UsuarioUpdateDTO();
        updateDTO.setNome("João Atualizado");
        updateDTO.setEmail("joao@email.com");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setId(1L);
        usuarioAtualizado.setNome("João Atualizado");
        usuarioAtualizado.setEmail("joao@email.com");

        UsuarioDTO usuarioDTOAtualizado = new UsuarioDTO();
        usuarioDTOAtualizado.setId(1L);
        usuarioDTOAtualizado.setNome("João Atualizado");
        usuarioDTOAtualizado.setEmail("joao@email.com");

        when(modelMapper.map(any(UsuarioUpdateDTO.class), eq(Usuario.class))).thenReturn(usuarioAtualizado);
        when(usuarioService.update(eq(1L), any(Usuario.class))).thenReturn(usuarioAtualizado);
        when(modelMapper.map(any(Usuario.class), eq(UsuarioDTO.class))).thenReturn(usuarioDTOAtualizado);

        mockMvc.perform(put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Atualizado"));
    }

    @Test
    void deveDeletarUsuario() throws Exception {
        doNothing().when(usuarioService).delete(1L);

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}