package br.com.senac.projectsolutions.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;
import br.com.senac.projectsolutions.View.DescricaoActivity;
import br.com.senac.projectsolutions.View.LoginActivity;

public class MainAdapter extends  RecyclerView.Adapter<MainHolder>{
    private Context context;
    private ArrayList<Produto> produtos;

    public MainAdapter(Context context, ArrayList<Produto> produtos){
        this.context = context;
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_produto, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, final int position) {
        holder.nomeProduto.setText(produtos.get(position).getNome());
        holder.precoProduto.setText(convertPreco(produtos.get(position).getValor()));
        holder.cardProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produto produto = produtos.get(position);
                Intent intent = new Intent(context, DescricaoActivity.class);

                intent.putExtra("codigoProduto", produto.getCodigo());
                intent.putExtra("nomeProduto", produto.getNome());
                intent.putExtra("descricaoProduto", produto.getDescricao());
                intent.putExtra("precoProduto", produto.getValor());
                intent.putExtra("tipoProduto", produto.getTipo());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    private String convertPreco(Double precoOriginal){
        String precoFormatted = String.format(Locale.US, "%.2f", precoOriginal);
        return "R$".concat(precoFormatted.replace(".", ","));
    }
}
