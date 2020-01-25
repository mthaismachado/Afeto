package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.team.afeto.R;

public class Dashboard_area_logada extends AppCompatActivity {

    private ImageView btn_Perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_area_logada);

        btn_Perfil = findViewById(R.id.btn_perfil);
        btn_Perfil.setOnClickListener(irPerfil);
    }

    private View.OnClickListener irPerfil = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), PerfilActivity.class));
        }
    };
}
