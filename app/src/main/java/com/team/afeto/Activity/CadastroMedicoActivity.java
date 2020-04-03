package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Medico;
import com.team.afeto.R;

import java.text.Normalizer;
import java.util.List;

public class CadastroMedicoActivity extends AppCompatActivity implements Validator.ValidationListener {
    //Campos de tela
    @Length(min = 5, message = "Tão pouco!?")
    private EditText mEspecialidades;
    @Length(min = 4, message = "Escreve o nome sem abrevição ;)")
    private EditText mBairro;
    @Length(min = 1, message = "Quais são as datas?")
    private EditText mDatas;
    private EditText mValores;
    private Button mBtn_concluido;
    private ImageView btn_Arrow_Back;
    //Firebase
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    //Validator
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_medico);

        mEspecialidades = findViewById(R.id.especialidade);
        mBairro = findViewById(R.id.bairro);
        mDatas = findViewById(R.id.datas);
        mValores = findViewById(R.id.valores);
        mBtn_concluido = findViewById(R.id.btn_concluido);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        validator = new Validator(this);
        validator.setValidationListener(this);

        mBtn_concluido.setOnClickListener(validaeGrava);
    }

    public String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


    @Override
    public void onValidationSucceeded() {
        Medico medico = new Medico();
        String especialidade = mEspecialidades.getText().toString().trim().toLowerCase();
        especialidade = removerAcentos(especialidade);
        medico.setEspecialidade(especialidade);
        String bairro = mBairro.getText().toString().trim().toLowerCase();
        bairro = removerAcentos(bairro);
        medico.setBairro(bairro);
        medico.setDatas(mDatas.getText().toString().trim());

        if(mValores.getText().toString().equals("") || mValores.getText().toString().equals("0")){
            medico.setValores("Gratuito");
        }else{
            medico.setValores(mValores.getText().toString().trim());
        }
        medico.setUidUsuario(mAuth.getCurrentUser().getUid());
        medico.setNome(UsuarioSingleton.getUsuario().getNome());

        gravarMedicoBase(medico);
    }


    private void gravarMedicoBase(Medico medico){
        db.collection("medicos").add(medico)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(CadastroMedicoActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Dashboard_area_logada.class));
                        finishAffinity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadastroMedicoActivity.this, "Algo saiu errado :(", Toast.LENGTH_SHORT).show();
            }
        });
}

    private View.OnClickListener validaeGrava = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validator.validate();
        }
    };

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
