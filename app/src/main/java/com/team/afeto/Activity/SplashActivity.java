package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Usuario usuario;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        usuario = new Usuario();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentUser != null) {
                    getUserInfo(currentUser);
                    startActivity(new Intent(getBaseContext(), Dashboard_area_logada.class));
                    finishAffinity();
                } else {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        }, 1500);
    }

    private void getUserInfo(FirebaseUser currentUser) {
        DocumentReference docRef = db.collection("users").document(currentUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        usuario.setNome(document.getData().get("nome").toString());
                        usuario.setCpf(document.getData().get("cpf").toString());
                        usuario.setGenero(document.getData().get("genero").toString());
                        usuario.setEmail(document.getData().get("email").toString());
                        usuario.setEstado(document.getData().get("estado").toString());
                        usuario.setCidade(document.getData().get("cidade").toString());
                        String comoAjuda = document.getData().get("comoAjuda").toString();
                        usuario.setComoAjuda(Arrays.asList(comoAjuda.substring(1, comoAjuda.length() - 1).split(",")));
//                        try {
//                            recuperaImagemPerfil();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
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

    private void recuperaImagemPerfil() throws IOException {
        FirebaseUser user = mAuth.getCurrentUser();

        final StorageReference perfilRef = mStorageRef.child("perfil_images/" + user.getUid() + ".jpg");

        final File localFile = File.createTempFile("images", "jpg");

        perfilRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                usuario.setUri_perfil(Uri.fromFile(localFile));
                UsuarioSingleton.setUsuario(usuario);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                UsuarioSingleton.setUsuario(usuario);
            }
        });
    }
}
