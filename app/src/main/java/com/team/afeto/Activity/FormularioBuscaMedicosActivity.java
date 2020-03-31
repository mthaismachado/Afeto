package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.team.afeto.R;

import java.util.List;

public class FormularioBuscaMedicosActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Length(min = 5, message = "Tão pouco!?")
    private EditText mEspecialidades;
    @Length(min = 4, message = "Escreve o nome sem abrevição ;)")
    private EditText mBairro;

    private Button mBtn_buscar;
    private ImageView btn_Arrow_Back;

    //Validator
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_busca_medicos);

        mEspecialidades = findViewById(R.id.especialidade);
        mBairro = findViewById(R.id.bairro);
        mBtn_buscar = findViewById(R.id.btn_buscar);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);


        validator = new Validator(this);
        validator.setValidationListener(this);

        mBtn_buscar.setOnClickListener(validareBuscar);

    }

    private View.OnClickListener validareBuscar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validator.validate();
        }
    };

    @Override
    public void onValidationSucceeded() {
        //TODO: Pensar na Lógica para redirecionar para a tela de listagem de médicos
    }

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

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
