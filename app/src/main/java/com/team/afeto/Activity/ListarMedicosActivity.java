package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.team.afeto.Helper.ListaMedicosRecyclerAdapter;
import com.team.afeto.Model.Medico;
import com.team.afeto.R;

import java.util.ArrayList;
import java.util.List;

public class ListarMedicosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    private List<Medico> mlista;

    private ImageView btn_Arrow_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_medicos);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String bairro = intent.getStringExtra("bairro");
        String especialidade = intent.getStringExtra("especialidade");

        //TODO: Fazer a query e buscar no Firebase

        mlista = new ArrayList<>();

        for(int i = 0; i<=10; i++){
            Medico medico = new Medico();
            medico.setNome("Ramon Calou");
            medico.setEspecialidade("Nutricionista");
            medico.setBairro("Taquara");
            medico.setTelefone("(21) 1234-5878");
            mlista.add(medico);
        }

        adapter = new ListaMedicosRecyclerAdapter(getApplicationContext(), mlista);
        mRecyclerView.setAdapter(adapter);
    }

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
