package com.team.afeto.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.team.afeto.R;

public class MainActivity extends AppCompatActivity {

    private Button queroAjudar;
    private Button btn_procurando_ajuda;
    private Button btn_Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queroAjudar = findViewById(R.id.btn_quero_ajudar);
        queroAjudar.setOnClickListener(quero_ajudar_activity);

        btn_procurando_ajuda = findViewById(R.id.btn_procurando_ajuda);
        btn_procurando_ajuda.setOnClickListener(quero_ajuda_activity);

        btn_Login = findViewById(R.id.btn_login);
        btn_Login.setOnClickListener(logIn);

    }

    private View.OnClickListener quero_ajudar_activity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener quero_ajuda_activity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Dashboard_preciso_ajuda.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener logIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    };

}
