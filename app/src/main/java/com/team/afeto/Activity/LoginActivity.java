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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button btn_login;
    @Email(message = "Digite um Email válido!")
    private EditText email;
    @Password(min = 8, message = "Insira a sua senha de 8 ou mais digitos")
    private EditText senha;
    private ImageView btn_Arrow_Back;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(acaoLogar);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);

        //Validator
        validator = new Validator(this);
        validator.setValidationListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private View.OnClickListener acaoLogar = new View.OnClickListener() {
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

    private void fazerLogin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            getUserInfo(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Confere os dados e tenta novamente. Pode ser a Internet também!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void getUserInfo(FirebaseUser currentUser){
        DocumentReference docRef = db.collection("users").document(currentUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Usuario usuario = new Usuario();
                        usuario.setNome(document.getData().get("nome").toString());
                        usuario.setCpf(document.getData().get("cpf").toString());
                        usuario.setGenero(document.getData().get("genero").toString());
                        usuario.setEmail(document.getData().get("email").toString());
                        usuario.setEstado(document.getData().get("estado").toString());
                        usuario.setCidade(document.getData().get("cidade").toString());
                        try{
                            String comoAjuda = document.getData().get("comoAjuda").toString();
                            usuario.setComoAjuda(Arrays.asList(comoAjuda.substring(1, comoAjuda.length() - 1).split(",")));
                        }catch(Exception e){
                            usuario.setComoAjuda(null);
                        }
                        UsuarioSingleton.setUsuario(usuario);
                        startActivity(new Intent(getBaseContext(), Dashboard_area_logada.class));
                        finishAffinity();
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }


    @Override
    public void onValidationSucceeded() {
        fazerLogin(email.getText().toString(), senha.getText().toString());
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
