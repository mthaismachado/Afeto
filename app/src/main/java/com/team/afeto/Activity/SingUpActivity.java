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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;

public class SingUpActivity extends AppCompatActivity {

    private Button btn_Login;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        btn_Login = findViewById(R.id.btn_login);
        btn_Login.setOnClickListener(cadastrar);

        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private View.OnClickListener cadastrar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           cadastrar(email.getText().toString(), senha.getText().toString());
        }
    };

    private boolean cadastrar(final String email, String senha){
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    //TODO: Criar função para salvar os dados do usuário no Firebase com o Uid como PK
                    String uid = user.getUid();
                    Usuario usuario = UsuarioSingleton.getUsuario();
                    usuario.setEmail(email);
                    UsuarioSingleton.setUsuario(usuario);

                    startActivity(new Intent(getApplicationContext(), Dashboard_area_logada.class));
                    finishAffinity();
                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SingUpActivity.this, "Algo deu errado",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
        return false;

    }
}
