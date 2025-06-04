package br.com.mcp.mcp_server.shared.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UsuarioFiltroDto {

    private Long id;

    private String nome;

    private String email;

    private String perfil;

    private String status;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataCriacaoInicial;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataCriacaoFinal;

    public UsuarioFiltroDto(String nome, String email, String perfil, String status,
            LocalDate dataCriacaoInicial, LocalDate dataCriacaoFinal) {
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.status = status;
        this.dataCriacaoInicial = dataCriacaoInicial;
        this.dataCriacaoFinal = dataCriacaoFinal;
    }

    public UsuarioFiltroDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataCriacaoInicial() {
        return dataCriacaoInicial;
    }
    
    public void setDataCriacaoInicial(LocalDate dataCriacaoInicial) {
        this.dataCriacaoInicial = dataCriacaoInicial;   
    }

    public LocalDate getDataCriacaoFinal() {
        return dataCriacaoFinal;
    }

    public void setDataCriacaoFinal(LocalDate dataCriacaoFinal) {
        this.dataCriacaoFinal = dataCriacaoFinal;
    }

}
