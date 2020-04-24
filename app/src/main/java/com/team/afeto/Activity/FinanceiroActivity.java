package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.afeto.R;

public class FinanceiroActivity extends AppCompatActivity {

    private ImageView mBtn_Arrow_Back;
    private TextView mTxtPrincipaisGastos;
    private TextView mTxtNovasPrioridades;
    private TextView mTxtConsumoConsciente;
    private TextView mTxtMedicosRemedios;
    private TextView mTxtNaoTiverRenda;
    private TextView btnPlusPrincipaisGastos;
    private TextView btnPlusNovasPrioridades;
    private TextView btnPlusConsumoConsciente;
    private TextView btnPlusMedicosRemedios;
    private TextView btnPlusNaoTiverRenda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro);

        mBtn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        mBtn_Arrow_Back.setOnClickListener(arrowBack);

        mTxtPrincipaisGastos = findViewById(R.id.txt_principaisGastos);
        mTxtNovasPrioridades = findViewById(R.id.txt_novasPrioridades);
        mTxtConsumoConsciente = findViewById(R.id.txt_consumo);
        mTxtMedicosRemedios = findViewById(R.id.txt_medicoseRemedios);
        mTxtNaoTiverRenda = findViewById(R.id.txt_naoTiveRenda);

        btnPlusPrincipaisGastos = findViewById(R.id.btnPlusPrincipaisGastos);
        btnPlusNovasPrioridades = findViewById(R.id.btnPlusNovasPrioridades);
        btnPlusConsumoConsciente = findViewById(R.id.btnPlusConsumoConsciente);
        btnPlusMedicosRemedios = findViewById(R.id.btnPlusMedicosRemedios);
        btnPlusNaoTiverRenda = findViewById(R.id.btnPlusNaoTiverRenda);

        btnPlusPrincipaisGastos.setOnClickListener(principaisGastos);
        btnPlusNovasPrioridades.setOnClickListener(novasPrioridades);
        btnPlusConsumoConsciente.setOnClickListener(consumoConsciente);
        btnPlusMedicosRemedios.setOnClickListener(medicosERemedios);
        btnPlusNaoTiverRenda.setOnClickListener(naoTiverRenda);


    }

    private View.OnClickListener principaisGastos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mTxtPrincipaisGastos.getVisibility() == View.GONE){
                mTxtPrincipaisGastos.setVisibility(View.VISIBLE);
            }else{
                mTxtPrincipaisGastos.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener novasPrioridades = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mTxtNovasPrioridades.getVisibility() == View.GONE){
                mTxtNovasPrioridades.setVisibility(View.VISIBLE);
            }else{
                mTxtNovasPrioridades.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener consumoConsciente = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mTxtConsumoConsciente.getVisibility() == View.GONE){
                mTxtConsumoConsciente.setVisibility(View.VISIBLE);
            }else{
                mTxtConsumoConsciente.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener medicosERemedios = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mTxtMedicosRemedios.getVisibility() == View.GONE){
                mTxtMedicosRemedios.setVisibility(View.VISIBLE);
            }else{
                mTxtMedicosRemedios.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener naoTiverRenda = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mTxtNaoTiverRenda.getVisibility() == View.GONE){
                mTxtNaoTiverRenda.setVisibility(View.VISIBLE);
            }else{
                mTxtNaoTiverRenda.setVisibility(View.GONE);
            }
        }
    };





    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
