package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.R;

public class Dashboard_area_logada extends AppCompatActivity {

    private ImageView mBtn_Perfil;
    private TextView mBtn_Medicos;
    private TextView mBtn_Doacoes;
    private TextView mBtn_Financeiro;
    private TextView mBtn_Forum;
    private ConstraintLayout mConfig_screen;
    private ImageView btn_Arrow_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_area_logada);

        mBtn_Perfil = findViewById(R.id.btn_perfil);
        mBtn_Medicos = findViewById(R.id.btn_medicos);
        mBtn_Doacoes = findViewById(R.id.btn_doacoes);
        mBtn_Financeiro = findViewById(R.id.btn_financeiro);
        mBtn_Forum = findViewById(R.id.btn_forum);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);

        mBtn_Perfil.setOnClickListener(irPerfil);
        mBtn_Doacoes.setOnClickListener(acaoBtnDoacoes);
        mBtn_Medicos.setOnClickListener(acaoBtnMedicos);
        mBtn_Financeiro.setOnClickListener(acaoBtnFinanceiro);

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
                //levar para a tela "Envio de Doação"
            }
        }
    };

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
