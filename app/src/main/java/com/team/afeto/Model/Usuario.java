package com.team.afeto.Model;

import java.util.List;

public class Usuario {

    private String nome;
    private String cpf;
    private String genero;
    private String estado;
    private String cidade;
    private String email;
    private List<String> comoAjuda;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<String> getComoAjuda() {
        return comoAjuda;
    }

    public void setComoAjuda(List<String> comoAjuda) {
        this.comoAjuda = comoAjuda;
    }
}
