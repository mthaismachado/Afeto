package com.team.afeto.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.afeto.Model.Doacao;
import com.team.afeto.R;

import java.util.List;

public class ListarDoacoesRecyclerAdapter extends RecyclerView.Adapter<ListarDoacoesRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Doacao> mLista;

    public ListarDoacoesRecyclerAdapter(Context context, List<Doacao> mLista) {
        this.context = context;
        this.mLista = mLista;
    }

    @NonNull
    @Override
    public ListarDoacoesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doacao_list_item_layout, viewGroup, false);
        return new ListarDoacoesRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListarDoacoesRecyclerAdapter.ViewHolder viewHolder, int position) {
        Doacao doacao = mLista.get(position);
        viewHolder.mTxtTitulo.setText(doacao.getTitulo());
        viewHolder.mTxtValor.setText(doacao.getValor());
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTxtTitulo;
        public TextView mTxtValor;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtTitulo = (TextView) itemView.findViewById(R.id.lblTitulo);
            mTxtValor = (TextView) itemView.findViewById(R.id.lblValor);


        }
    }

}
