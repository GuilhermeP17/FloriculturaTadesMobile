package br.com.senac.projectsolutions.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.senac.projectsolutions.R;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoHolder> {
    @NonNull
    @Override
    public CarrinhoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarrinhoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_produto_carrinho, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CarrinhoHolder holder, final int position) {
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeManager(true, holder.quantidadeProdutos);
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeManager(false, holder.quantidadeProdutos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    private void quantidadeManager(boolean adicionando, TextView quantidade) {
        if (adicionando) {
            int qtdAtual = Integer.parseInt(quantidade.getText().toString());
            quantidade.setText(String.valueOf(qtdAtual + 1));
        } else {
            int qtdAtual = Integer.parseInt(quantidade.getText().toString());
            if (qtdAtual > 1) {
                quantidade.setText(String.valueOf(qtdAtual - 1));
            }
        }
    }
}
