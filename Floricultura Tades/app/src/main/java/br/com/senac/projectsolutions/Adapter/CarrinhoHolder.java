package br.com.senac.projectsolutions.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import br.com.senac.projectsolutions.R;

public class CarrinhoHolder extends RecyclerView.ViewHolder {
    protected TextView nomeProduto, precoProduto, quantidadeProdutos;
    protected MaterialButton btnAdd, btnRemove;
    protected ImageView btnDelete;

    public CarrinhoHolder(@NonNull View itemView) {
        super(itemView);
        nomeProduto = itemView.findViewById(R.id.nome_produto);
        precoProduto = itemView.findViewById(R.id.preco_produto);
        quantidadeProdutos = itemView.findViewById(R.id.quantidade);
        btnAdd = itemView.findViewById(R.id.btn_add);
        btnRemove = itemView.findViewById(R.id.btn_remove);
        btnDelete = itemView.findViewById(R.id.delete_item);
    }
}
