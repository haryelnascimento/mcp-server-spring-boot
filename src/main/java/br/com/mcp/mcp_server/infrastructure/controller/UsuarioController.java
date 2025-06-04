package br.com.mcp.mcp_server.infrastructure.controller;

import br.com.mcp.mcp_server.domain.model.Usuario;
import br.com.mcp.mcp_server.domain.service.UsuarioService;
import br.com.mcp.mcp_server.shared.dto.UsuarioDTO;
import br.com.mcp.mcp_server.shared.dto.UsuarioFiltroDto;
import br.com.mcp.mcp_server.shared.dto.UsuarioSaveDTO;
import br.com.mcp.mcp_server.shared.dto.UsuarioUpdateDTO;
import jakarta.validation.Valid;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    public UsuarioController(UsuarioService usuarioService, ModelMapper modelMapper) {
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }
    
    @GetMapping
    @Tool(name = "consultarUsuariosPorFiltros", description = "Consultar de usuários por filtros.")
    public ResponseEntity<Page<UsuarioDTO>> consultar(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String perfil,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataCriacaoInicial,
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataCriacaoFinal,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
       // @RequestParam(defaultValue = "id,desc") String[] sort
    ) {

        UsuarioFiltroDto filter = new UsuarioFiltroDto(nome, email, perfil, status, dataCriacaoInicial, dataCriacaoFinal);

        return ResponseEntity.ok(usuarioService.search(filter, PageRequest.of(page, size))
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)));
    }

    @GetMapping("/{id}")
    @Tool(name = "consultarPorId", description = "Buscar um usuário pelo ID.")
    public ResponseEntity<UsuarioDTO> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper
                .map(usuarioService.findById(id), UsuarioDTO.class));
    }

    @PostMapping
    @Tool(name = "criarUsuario", description = "Cria um novo usuário.")
    public ResponseEntity<UsuarioDTO> criar(@RequestBody @Valid UsuarioSaveDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        UsuarioDTO usuarioDTO = modelMapper.map(usuarioService.create(usuario), UsuarioDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    @PutMapping("/{id}")
    @Tool(name = "atualizarUsuario", description = "Atualiza um usuário.")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        UsuarioDTO usuarioDTO = modelMapper.map(usuarioService.update(id, usuario), UsuarioDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    @DeleteMapping("/{id}")
    @Tool(name = "deletarUsuarioPorId", description = "Deleta um usuário pelo ID.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
