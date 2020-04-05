package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team.afeto.Helper.ListaMedicosRecyclerAdapter;
import com.team.afeto.Model.Medico;
import com.team.afeto.R;

import java.util.ArrayList;
import java.util.List;

public class ListarMedicosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    private List<Medico> mlista;
    FirebaseFirestore db;

    private TextView mNaoEncontramosMedicos;

    private ImageView btn_Arrow_Back;

    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_medicos);

        db = FirebaseFirestore.getInstance();

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);
        mNaoEncontramosMedicos = findViewById(R.id.naoEncontramosMedicos);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        realizaConsultaMedicos();

    }

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void realizaConsultaMedicos(){
        mProgressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        String bairro = intent.getStringExtra("bairro");
        String especialidade = intent.getStringExtra("especialidade");
        mlista = new ArrayList<>();

        // Create a reference to the cities collection
        CollectionReference medicosRef = db.collection("medicos");

        medicosRef.whereEqualTo("bairro", bairro)
                .whereEqualTo("especialidade", especialidade)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Medico medico = new Medico();
                        medico.setNome(document.getString("nome"));
                        medico.setBairro(document.getString("bairro"));
                        medico.setEspecialidade(document.getString("especialidade"));
                        medico.setTelefone(document.getString("telefone"));
                        mlista.add(medico);
                    }
                } else {
                    Toast.makeText(ListarMedicosActivity.this, "Tivemos problemas ao consultar", Toast.LENGTH_LONG).show();
                }
                adapter = new ListaMedicosRecyclerAdapter(getApplicationContext(), mlista);
                mRecyclerView.setAdapter(adapter);
                mProgressBar.setVisibility(View.GONE);
                if(mlista.isEmpty()){
                    mNaoEncontramosMedicos.setVisibility(View.VISIBLE);
                }
            }
        });
        adapter = new ListaMedicosRecyclerAdapter(getApplicationContext(), mlista);
        mRecyclerView.setAdapter(adapter);
    }
}
