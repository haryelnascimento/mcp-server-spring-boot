package br.com.mcp.mcp_server.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.mcp.mcp_server.domain.model.Usuario;
import br.com.mcp.mcp_server.shared.dto.UsuarioFiltroDto;
import jakarta.persistence.criteria.Predicate;

public class UsuarioSpecs {

    public static Specification<Usuario> search(UsuarioFiltroDto filtro) {
        
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getId() != null) {
                predicates.add(builder.equal(root.get("id"), filtro.getId()));
            }


            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicates.add(builder.like(builder.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
                predicates.add(builder.like(builder.lower(root.get("email")), "%" + filtro.getEmail().toLowerCase() + "%"));
            }

            if (filtro.getPerfil() != null && !filtro.getPerfil().isEmpty()) {
                predicates.add(builder.equal(builder.upper(root.get("perfil")), filtro.getPerfil().toUpperCase()));
            }

            if (filtro.getStatus() != null && !filtro.getStatus().isEmpty()) {
                predicates.add(builder.equal(builder.upper(root.get("status")), filtro.getStatus().toUpperCase()));
            }

            if (filtro.getDataCriacaoInicial() != null) {
                predicates.add(
                    builder.greaterThanOrEqualTo(
                        root.get("dataCriacao"),
                        filtro.getDataCriacaoInicial().atStartOfDay() 
                    )
                );
            }

            if (filtro.getDataCriacaoFinal() != null) {
                predicates.add(
                    builder.lessThanOrEqualTo(
                        root.get("dataCriacao"),
                        filtro.getDataCriacaoFinal().atTime(23, 59, 59)
                    )
                );
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
