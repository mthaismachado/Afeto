package com.team.afeto.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        if(doacao.getBairro() != null){
            String strdoacao = doacao.getBairro().substring(0,1).toUpperCase() + doacao.getBairro().substring(1);
            viewHolder.mTxtBairro.setText(strdoacao);
        }
        if(doacao.getUrlDownloadImage() != null){
            Glide.with(context).load(doacao.getUrlDownloadImage()).into(viewHolder.mDoacaoFoto);
        }

    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTxtTitulo;
        public TextView mTxtValor;
        public TextView mTxtBairro;
        public ImageView mDoacaoFoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtTitulo = (TextView) itemView.findViewById(R.id.lblTitulo);
            mTxtValor = (TextView) itemView.findViewById(R.id.lblValor);
            mTxtBairro = (TextView) itemView.findViewById(R.id.lblBairro);
            mDoacaoFoto = (ImageView) itemView.findViewById(R.id.doacao_image);
        }
    }

}
