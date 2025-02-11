package com.team.afeto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;

import java.util.ArrayList;
import java.util.List;

public class CadastroAjudanteActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Length(min = 10, message = "Escreve seu nome completo :)")
    private EditText nomeCompleto;

    @Length(min = 14, message = "Acho que está faltando número")
    private EditText cpf;

    private EditText genero;
    @Length(min = 5, message = "Qual o estado que você mora?")
    private EditText estado;

    @Length(min = 5, message = "Qual a cidade que você mora?")
    private EditText cidade;

    private CheckBox check_voluntariado;
    private CheckBox check_saude;
    private CheckBox check_doacoes;
    private CheckBox check_outros;

    private Button btn_Enviar;
    private ImageView btn_Arrow_Back;

    private List<String> comoAjuda = new ArrayList<>();

    private Validator validator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ajudante);

        //FindView
        nomeCompleto = findViewById(R.id.nomecompleto);
        cpf = findViewById(R.id.cpf);
        genero = findViewById(R.id.genero);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);
        check_voluntariado = findViewById(R.id.radio_voluntariado);
        check_saude = findViewById(R.id.radio_saude);
        check_doacoes = findViewById(R.id.radio_doacoes);
        check_outros = findViewById(R.id.radio_outros);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);

        //Mascara CPF
        SimpleMaskFormatter cpf_mask = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(cpf, cpf_mask);
        cpf.addTextChangedListener(mtw);

        //Validator
        validator = new Validator(this);
        validator.setValidationListener(this);

        //Button Enviar
        btn_Enviar = findViewById(R.id.btn_enviar);
        btn_Enviar.setOnClickListener(irLogin);
    }

    private View.OnClickListener irLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            extrairOpcoesRadio();
            if(comoAjuda.size() == 0){
                Toast.makeText(CadastroAjudanteActivity.this, "Selecione pelo menos uma opção de ajuda!", Toast.LENGTH_SHORT).show();
            }else{
                validator.validate();
            }
        }
    };

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };



    private void extrairOpcoesRadio(){
        comoAjuda.clear();
      if(check_voluntariado.isChecked()){
          comoAjuda.add(check_voluntariado.getText().toString());
      }
      if(check_saude.isChecked()){
          comoAjuda.add(check_saude.getText().toString());
      }
      if(check_doacoes.isChecked()){
          comoAjuda.add(check_doacoes.getText().toString());
      }
      if(check_outros.isChecked()){
          comoAjuda.add(check_outros.getText().toString());
      }
    }

    @Override
    public void onValidationSucceeded() {
        Usuario usuario = new Usuario();
        usuario.setNome(nomeCompleto.getText().toString());
        usuario.setCpf(cpf.getText().toString());
        usuario.setGenero(genero.getText().toString());
        usuario.setEstado(estado.getText().toString());
        usuario.setCidade(cidade.getText().toString());

        usuario.setComoAjuda(comoAjuda);
        UsuarioSingleton.setUsuario(usuario);

        Intent intent = new Intent(getApplicationContext(), SingUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
