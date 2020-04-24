package com.team.afeto.Model;

import android.net.Uri;

public class Doacao {

    private String categoria;
    private String titulo;
    private String valor;
    private String bairro;
    private String uidUsuario;
    private Uri uri_fotoDoacao;
    private String urlDownloadImage;

    public Doacao() {
    }

    public Doacao(String categoria, String titulo, String valor, String bairro, String uidUsuario, Uri uri_fotoDoacao, String urlDownloadImage) {
        this.categoria = categoria;
        this.titulo = titulo;
        this.valor = valor;
        this.bairro = bairro;
        this.uidUsuario = uidUsuario;
        this.uri_fotoDoacao = uri_fotoDoacao;
        this.urlDownloadImage = urlDownloadImage;
    }

    public String getUrlDownloadImage() {
        return urlDownloadImage;
    }

    public void setUrlDownloadImage(String urlDownloadImage) {
        this.urlDownloadImage = urlDownloadImage;
    }

    public Uri getUri_fotoDoacao() {
        return uri_fotoDoacao;
    }

    public void setUri_fotoDoacao(Uri uri_fotoDoacao) {
        this.uri_fotoDoacao = uri_fotoDoacao;
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
