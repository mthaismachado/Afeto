package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.team.afeto.R;

public class CadastroProntoActivity extends AppCompatActivity {

    private Button mBtn_voltar;
    private ImageView btn_Arrow_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pronto);

        mBtn_voltar = findViewById(R.id.btn_voltar);
        mBtn_voltar.setOnClickListener(voltar);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(voltar);
    }

    private View.OnClickListener voltar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Dashboard_area_logada.class);
            startActivity(intent);
        }
    };


}
