package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentUser != null){
                    getUserInfo(currentUser);
                    startActivity(new Intent(getBaseContext(), Dashboard_area_logada.class));
                    finishAffinity();
                }else {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        }, 1500);
    }

    private void getUserInfo(FirebaseUser currentUser){
        DocumentReference docRef = db.collection("users").document(currentUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        usuario = new Usuario();
                        usuario.setNome(document.getData().get("nome").toString());
                        usuario.setEmail(document.getData().get("email").toString());
                        usuario.setEstado(document.getData().get("estado").toString());
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
