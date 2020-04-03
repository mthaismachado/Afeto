package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.team.afeto.R;

public class FormularioBuscaDoacoesActivity extends AppCompatActivity {

    private static final String[] ITENS_DOACAO = new String[]{"Higiene", "Casa", "Acessórios", "Roupas", "Bebê"};

    private Button mBtn_buscar;
    private Spinner mSpinner_categorias;
    private ImageView btn_Arrow_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_busca_doacoes);

        mSpinner_categorias = findViewById(R.id.spinner_categorias);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ITENS_DOACAO );
        mSpinner_categorias.setAdapter(adapter);

        mBtn_buscar = findViewById(R.id.btn_buscar);
        mBtn_buscar.setOnClickListener(buscarDoacoes);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);


    }

    private View.OnClickListener buscarDoacoes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ListarDoacoesActivity.class);
            intent.putExtra("categoria", mSpinner_categorias.getSelectedItem().toString());
            startActivity(intent);
        }
    };

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

}
