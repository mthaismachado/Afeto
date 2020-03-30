package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team.afeto.R;

public class CadastroProntoActivity extends AppCompatActivity {

    private Button mBtn_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pronto);

        mBtn_voltar = findViewById(R.id.btn_voltar);
        mBtn_voltar.setOnClickListener(voltar);
    }

    private View.OnClickListener voltar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Dashboard_area_logada.class);
            startActivity(intent);
        }
    };


}
