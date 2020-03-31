package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.team.afeto.R;

public class FormularioBuscaDoacoesActivity extends AppCompatActivity {

    private static final String[] ITENS_DOACAO = new String[]{"Higiene", "Casa", "Acessórios", "Roupas", "Bebê"};

    private Button mBtn_buscar;
    private AutoCompleteTextView editText;
    private ImageView btn_Arrow_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_busca_doacoes);

        editText = findViewById(R.id.edt_categorias);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ITENS_DOACAO );
        editText.setAdapter(adapter);

        mBtn_buscar = findViewById(R.id.btn_buscar);
        mBtn_buscar.setOnClickListener(buscarDoacoes);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);


    }

    private View.OnClickListener buscarDoacoes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: Esperar a tela de doações =D
        }
    };

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

}
