package br.com.senac.projectsolutions.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.senac.projectsolutions.R;

public class MainHolder extends RecyclerView.ViewHolder {
    protected TextView nomeProduto, descricaoProduto;

    public MainHolder(@NonNull View itemView) {
        super(itemView);
        nomeProduto = itemView.findViewById(R.id.nome_produto);
        descricaoProduto = itemView.findViewById(R.id.descricao_produto);
    }
}
