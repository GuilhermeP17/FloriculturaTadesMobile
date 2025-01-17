package br.com.senac.projectsolutions.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import br.com.senac.projectsolutions.R;

public class MainHolder extends RecyclerView.ViewHolder {
    protected TextView nomeProduto, precoProduto;
    protected CardView cardProduto;

    public MainHolder(@NonNull View itemView) {
        super(itemView);
        nomeProduto = itemView.findViewById(R.id.nome_produto);
        precoProduto = itemView.findViewById(R.id.preco_produto);
        cardProduto = itemView.findViewById(R.id.card_produto);
    }
}
