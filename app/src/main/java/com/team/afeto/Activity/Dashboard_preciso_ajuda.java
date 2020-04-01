package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.R;

public class Dashboard_preciso_ajuda extends AppCompatActivity {

    private ImageView mBtn_Arrow_Back;
    private TextView mBtn_Medicos;
    private TextView mBtn_Doacoes;
    private TextView mBtn_Financeiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_preciso_ajuda);

        mBtn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        mBtn_Arrow_Back.setOnClickListener(arrowBack);

        mBtn_Doacoes = findViewById(R.id.btn_doacoes);
        mBtn_Doacoes.setOnClickListener(doacoes);
        mBtn_Medicos = findViewById(R.id.btn_medicos);
        mBtn_Financeiro = findViewById(R.id.btn_financeiro);

        mBtn_Medicos.setOnClickListener(acaoBtnMedicos);
        mBtn_Financeiro.setOnClickListener(acaoBtnFinanceiro);

    }

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener doacoes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CadastroSolicitanteActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener acaoBtnMedicos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), FormularioBuscaMedicosActivity.class);
            startActivity(intent);
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
