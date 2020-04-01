package com.team.afeto.Helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.afeto.Model.Medico;
import com.team.afeto.R;

import java.util.List;

public class ListaMedicosRecyclerAdapter extends RecyclerView.Adapter<ListaMedicosRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Medico> mLista;

    public ListaMedicosRecyclerAdapter(Context context, List<Medico> mLista) {
        this.context = context;
        this.mLista = mLista;
    }

    @NonNull
    @Override
    public ListaMedicosRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medicos_list_item_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListaMedicosRecyclerAdapter.ViewHolder viewHolder, int position) {
        Medico medico = mLista.get(position);
        viewHolder.mTxtNome.setText(medico.getNome());
        viewHolder.mTxtEspecialidade.setText(medico.getEspecialidade());
        viewHolder.mTxtBairro.setText(medico.getBairro());
        viewHolder.mTxtTelefone.setText(medico.getTelefone());
        viewHolder.mBtn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + viewHolder.mTxtTelefone.getText().toString()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTxtNome;
        public TextView mTxtEspecialidade;
        public TextView mTxtBairro;
        public TextView mTxtTelefone;
        public ImageView mBtn_call;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtNome = (TextView) itemView.findViewById(R.id.lblNome);
            mTxtEspecialidade = (TextView) itemView.findViewById(R.id.lblEspecialidade);
            mTxtBairro = (TextView) itemView.findViewById(R.id.lblBairro);
            mTxtTelefone = (TextView) itemView.findViewById(R.id.lblTelefone);
            mBtn_call = (ImageView) itemView.findViewById(R.id.btn_call);


        }
    }

}
