package com.team.afeto.Model;

public class Medico {

    private String especialidade;
    private String bairro;
    private String datas;
    private String valores;
    private String uidUsuario;


    public Medico() {
    }

    public Medico(String especialidade, String bairro, String datas, String valores, String uidUsuario) {
        this.especialidade = especialidade;
        this.bairro = bairro;
        this.datas = datas;
        this.valores = valores;
        this.uidUsuario = uidUsuario;
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
