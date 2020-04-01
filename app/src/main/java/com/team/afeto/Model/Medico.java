package com.team.afeto.Model;

public class Medico {

    private String nome;
    private String especialidade;
    private String bairro;
    private String datas;
    private String valores;
    private String uidUsuario;
    private String telefone;


    public Medico() {
    }


    public Medico(String nome, String especialidade, String bairro, String datas, String valores, String uidUsuario, String telefone) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.bairro = bairro;
        this.datas = datas;
        this.valores = valores;
        this.uidUsuario = uidUsuario;
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public String getValores() {
        return valores;
    }

    public void setValores(String valores) {
        this.valores = valores;
    }
}
