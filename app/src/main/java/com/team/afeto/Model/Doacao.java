package com.team.afeto.Model;

import android.net.Uri;

public class Doacao {

    private String categoria;
    private String titulo;
    private String valor;
    private String bairro;
    private String uidUsuario;
    private Uri uri_perfil;

    public Doacao() {
    }

    public Doacao(String categoria, String titulo, String valor, String bairro, String uidUsuario, Uri uri_perfil) {
        this.categoria = categoria;
        this.titulo = titulo;
        this.valor = valor;
        this.bairro = bairro;
        this.uidUsuario = uidUsuario;
        this.uri_perfil = uri_perfil;
    }

    public Uri getUri_perfil() {
        return uri_perfil;
    }

    public void setUri_perfil(Uri uri_perfil) {
        this.uri_perfil = uri_perfil;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

}
