package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;

import java.io.File;
import java.io.IOException;

public class Dashboard_area_logada extends AppCompatActivity {

    private ImageView mBtn_abre_fake_drawer;
    private TextView mBtn_Medicos;
    private TextView mBtn_Doacoes;
    private TextView mBtn_Financeiro;
    private TextView mBtn_Forum;
    private TextView mBtn_ir_perfil;
    private TextView mLbl_nome_usuario;
    private ImageView mProfile_image;
    private ConstraintLayout mConfig_screen;
    private ImageView btn_Arrow_Back;
    private Button mBtn_quem_somos;
    private Button mBtn_perguntas_frequentes;
    private Button mBtn_fale_com_a_gente;
    ConstraintLayout home_dash;
    ConstraintLayout fakeDrawer;

    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_area_logada);

        mBtn_abre_fake_drawer = findViewById(R.id.abre_fake_drawer);
        mBtn_Medicos = findViewById(R.id.btn_medicos);
        mBtn_Doacoes = findViewById(R.id.btn_doacoes);
        mBtn_Financeiro = findViewById(R.id.btn_financeiro);
        mBtn_Forum = findViewById(R.id.btn_forum);

        home_dash = findViewById(R.id.view_dash_logada);
        fakeDrawer = findViewById(R.id.fake_drawer);
        mBtn_quem_somos = findViewById(R.id.btn_quem_somos);
        mBtn_perguntas_frequentes = findViewById(R.id.btn_perguntas_frequentes);
        mBtn_fale_com_a_gente = findViewById(R.id.btn_fale_com_a_gente);
        mProfile_image = findViewById(R.id.profile_image);
        mLbl_nome_usuario = findViewById(R.id.lbl_nome_usuario);

        mBtn_ir_perfil = findViewById(R.id.btn_ir_perfil);
        mBtn_ir_perfil.setOnClickListener(irPerfil);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);
        btn_Arrow_Back.setEnabled(false);

        mBtn_abre_fake_drawer.setOnClickListener(abrirFakeDrawer);
        mBtn_Doacoes.setOnClickListener(acaoBtnDoacoes);
        mBtn_Medicos.setOnClickListener(acaoBtnMedicos);
        mBtn_Financeiro.setOnClickListener(acaoBtnFinanceiro);
        mBtn_quem_somos.setOnClickListener(ir_perguntas_frequentes);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    private View.OnClickListener irPerfil = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (UsuarioSingleton.getUsuario() != null) {
                startActivity(new Intent(getApplicationContext(), PerfilActivity.class));
            }
        }
    };

    private View.OnClickListener acaoBtnDoacoes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (UsuarioSingleton.getUsuario().getComoAjuda() == null) {
                Intent intent = new Intent(getApplicationContext(), FormularioBuscaDoacoesActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), CadastroDoacaoActivity.class);
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void recuperaImagemPerfil() throws IOException {
        if (UsuarioSingleton.getUsuario().getUri_perfil() == null) {
            FirebaseUser user = mAuth.getCurrentUser();

            final StorageReference perfilRef = mStorageRef.child("perfil_images/" + user.getUid() + ".jpg");

            final File localFile = File.createTempFile(user.getUid(), "jpg", getCacheDir());

            perfilRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Uri uri = Uri.fromFile(localFile);
                    UsuarioSingleton.getUsuario().setUri_perfil(uri);
                    mProfile_image.setImageURI(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
    }


    private View.OnClickListener ir_perguntas_frequentes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), QuemSomosActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener abrirFakeDrawer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fakeDrawer.setVisibility(View.VISIBLE);
            for (int i = 0; i < home_dash.getChildCount(); i++) {
                View child = home_dash.getChildAt(i);
                child.setEnabled(false);
            }
            if(UsuarioSingleton.getUsuario() != null && UsuarioSingleton.getUsuario().getUri_perfil() == null){
                try {
                    mLbl_nome_usuario.setText(UsuarioSingleton.getUsuario().getNome());
                    recuperaImagemPerfil();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(UsuarioSingleton.getUsuario().getUri_perfil() != null){
                mProfile_image.setImageURI(UsuarioSingleton.getUsuario().getUri_perfil());
            }
            btn_Arrow_Back.setEnabled(true);
        }
    };

    @Override
    public void onBackPressed() {
        if(fakeDrawer.getVisibility() == View.VISIBLE){
            fakeDrawer.setVisibility(View.GONE);
            for (int i = 0; i < home_dash.getChildCount(); i++) {
                View child = home_dash.getChildAt(i);
                child.setEnabled(true);
            }
            btn_Arrow_Back.setEnabled(false);
        }else{
            super.onBackPressed();
        }

    }

    private View.OnClickListener acaoBtnMedicos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (UsuarioSingleton.getUsuario().getComoAjuda() == null || !UsuarioSingleton.getUsuario().getComoAjuda().contains("Saude")) {
                Intent intent = new Intent(getApplicationContext(), FormularioBuscaMedicosActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), CadastroMedicoActivity.class);
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener acaoBtnFinanceiro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), FinanceiroActivity.class);
            startActivity(intent);
        }
    };


}
