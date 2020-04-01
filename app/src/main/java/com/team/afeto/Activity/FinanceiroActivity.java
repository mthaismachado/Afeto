package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.team.afeto.R;

public class FinanceiroActivity extends AppCompatActivity {

    private ImageView mBtn_Arrow_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro);

        mBtn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        mBtn_Arrow_Back.setOnClickListener(arrowBack);
    }
    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
