package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team.afeto.Helper.ListaMedicosRecyclerAdapter;
import com.team.afeto.Helper.ListarDoacoesRecyclerAdapter;
import com.team.afeto.Model.Doacao;
import com.team.afeto.R;

import java.util.ArrayList;
import java.util.List;

public class ListarDoacoesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    private List<Doacao> mlista;
    FirebaseFirestore db;

    private ImageView btn_Arrow_Back;
    private TextView mLblCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_doacoes);

        db = FirebaseFirestore.getInstance();

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);

        mLblCategoria = findViewById(R.id.lblCategoria);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String categoria = intent.getStringExtra("categoria");

        mLblCategoria.setText(categoria);

        realizaConsultaDoacoes(categoria);
    }

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void realizaConsultaDoacoes(String categoria){

        mlista = new ArrayList<>();

        // Create a reference to the cities collection
        CollectionReference doacoesRef = db.collection("doacoes");

        doacoesRef.whereEqualTo("categoria", categoria)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Doacao doacao = new Doacao();
                                doacao.setTitulo(document.getString("titulo"));
                                doacao.setValor(document.getString("valor"));
                                mlista.add(doacao);

                            }
                        } else {
                            Toast.makeText(ListarDoacoesActivity.this, "Tivemos problemas ao consultar", Toast.LENGTH_LONG).show();
                        }
                        adapter = new ListarDoacoesRecyclerAdapter(getApplicationContext(), mlista);
                        mRecyclerView.setAdapter(adapter);
                    }
                });

        adapter = new ListarDoacoesRecyclerAdapter(getApplicationContext(), mlista);
        mRecyclerView.setAdapter(adapter);
    }

}
