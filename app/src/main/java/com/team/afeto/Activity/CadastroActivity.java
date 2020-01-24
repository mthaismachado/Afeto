package com.team.afeto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText nomeCompleto;
    private EditText cpf;
    private EditText genero;
    private EditText estado;
    private EditText cidade;
    private Button btn_Enviar;
    private RadioButton radio_Voluntariado;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeCompleto = findViewById(R.id.nomecompleto);
        cpf = findViewById(R.id.cpf);
        genero = findViewById(R.id.genero);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);
        radio_Voluntariado = findViewById(R.id.radio_voluntariado);

       // radio_Voluntariado.setOnClickListener(click_radio_volutariado);


        //TODO: Recuperar as informações dos RadioButtons e ver como faz para desmarcar quando apertar e já estiver marcado

        btn_Enviar = findViewById(R.id.btn_enviar);
        btn_Enviar.setOnClickListener(irLogin);
    }

    private View.OnClickListener irLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Usuario usuario = new Usuario();
            usuario.setNome(nomeCompleto.getText().toString());
            usuario.setCpf(cpf.getText().toString());
            usuario.setGenero(genero.getText().toString());
            usuario.setEstado(estado.getText().toString());
            usuario.setCidade(cidade.getText().toString());

            UsuarioSingleton.setUsuario(usuario);

            Intent intent = new Intent(getApplicationContext(), SingUpActivity.class);
            startActivity(intent);
        }
    };

//    private View.OnClickListener click_radio_volutariado = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if(radio_Voluntariado.isChecked()){
//                radio_Voluntariado.setChecked(false);
//            }
//        }
//    };
}
