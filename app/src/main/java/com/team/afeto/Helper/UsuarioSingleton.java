package com.team.afeto.Helper;

import com.team.afeto.Model.Usuario;

public class UsuarioSingleton {

private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        UsuarioSingleton.usuario = usuario;
    }
}
