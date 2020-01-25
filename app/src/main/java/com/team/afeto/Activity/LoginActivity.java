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
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button btn_login;
    private EditText email;
    private EditText senha;
    private ImageView btn_Arrow_Back;

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


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private View.OnClickListener acaoLogar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fazerLogin(email.getText().toString(), senha.getText().toString());
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
                            startActivity(new Intent(getBaseContext(), Dashboard_area_logada.class));
                            finishAffinity();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Algo deu Errado.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
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
                        String comoAjuda = document.getData().get("comoAjuda").toString();
                        usuario.setComoAjuda(Arrays.asList(comoAjuda.substring(1, comoAjuda.length()-1).split(",")));
                        UsuarioSingleton.setUsuario(usuario);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }


}
