package br.com.senac.projectsolutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;

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
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        holder.nomeProduto.setText(produtos.get(position).getNome());
        holder.precoProduto.setText(convertPreco(produtos.get(position).getValor()));
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
