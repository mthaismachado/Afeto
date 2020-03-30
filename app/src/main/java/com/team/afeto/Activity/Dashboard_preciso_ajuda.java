package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.afeto.R;

public class Dashboard_preciso_ajuda extends AppCompatActivity {

    private ImageView mBtn_Arrow_Back;
    private TextView mBtn_doacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_preciso_ajuda);

        mBtn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        mBtn_Arrow_Back.setOnClickListener(arrowBack);

        mBtn_doacoes = findViewById(R.id.btn_doacoes);
        mBtn_doacoes.setOnClickListener(doacoes);

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
}
