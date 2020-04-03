package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;
import java.util.List;

public class CadastroSolicitanteActivity extends AppCompatActivity implements Validator.ValidationListener {
    @Length(min = 10, message = "Escreve seu nome completo :)")
    private EditText nomeCompleto;

    @Length(min = 14, message = "Acho que está faltando número")
    private EditText cpf;

    @Length(min = 5, message = "Qual o estado que você mora?")
    private EditText estado;

    @Length(min = 5, message = "Qual a cidade que você mora?")
    private EditText cidade;

    @Email(message = "Digita um Email para fazer a sua conta")
    private EditText email;
    @Length(min = 8, message = "Precisa ter 8 dígitos")
    private EditText senha;

    private Button btn_Enviar;
    private ImageView btn_Arrow_Back;

    private Validator validator;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_solicitante);

        //FindView
        nomeCompleto = findViewById(R.id.nomecompleto);
        cpf = findViewById(R.id.cpf);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);

        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);

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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
    }

    private View.OnClickListener irLogin = new View.OnClickListener() {
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
    public void onValidationSucceeded() {
        Usuario usuario = new Usuario();
        usuario.setNome(nomeCompleto.getText().toString());
        usuario.setCpf(cpf.getText().toString());
        usuario.setGenero("Feminino");
        usuario.setEstado(estado.getText().toString());
        usuario.setCidade(cidade.getText().toString());

        usuario.setComoAjuda(null);
        UsuarioSingleton.setUsuario(usuario);

        cadastrar(email.getText().toString(), senha.getText().toString());
    }

    private void cadastrar(final String email, String senha){
        //Função que realiza o cadastro
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Com o usuário cadastrado, os dados são guardados no DB com o uid como ID
                    String uid = user.getUid();
                    Usuario usuario = UsuarioSingleton.getUsuario();
                    usuario.setEmail(email);
                    UsuarioSingleton.setUsuario(usuario);

                    db.collection("users").document(uid)
                            .set(usuario)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(getApplicationContext(), CadastroProntoActivity.class));
                                    finishAffinity();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CadastroSolicitanteActivity.this, "Algo saiu errado :(", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CadastroSolicitanteActivity.this, "Algo deu errado",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
