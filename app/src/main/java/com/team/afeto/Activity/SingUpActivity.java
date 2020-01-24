package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class SingUpActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Button btn_Login;
    private FirebaseAuth mAuth;
    @Email(message = "Digita um Email para fazer a sua conta")
    private EditText email;
    @Length(min = 8, message = "Precisa ter 8 dígitos")
    private EditText senha;
    private Validator validator;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        db = FirebaseFirestore.getInstance();

        btn_Login = findViewById(R.id.btn_login);
        btn_Login.setOnClickListener(cadastrar);

        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);

        validator = new Validator(this);
        validator.setValidationListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private View.OnClickListener cadastrar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           validator.validate();
        }
    };

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
                                    Toast.makeText(SingUpActivity.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Dashboard_area_logada.class));
                                    finishAffinity();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SingUpActivity.this, "Algo saiu errado :(", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SingUpActivity.this, "Algo deu errado",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        cadastrar(email.getText().toString(), senha.getText().toString());
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
