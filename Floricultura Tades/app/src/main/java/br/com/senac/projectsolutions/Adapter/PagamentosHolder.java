package br.com.senac.projectsolutions.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.senac.projectsolutions.R;

public class PagamentosHolder extends RecyclerView.ViewHolder {

    public TextView tipoPagamento, numCartao, nomeTitular, vencimento;

    public PagamentosHolder(@NonNull View itemView) {
        super(itemView);
        tipoPagamento = itemView.findViewById(R.id.tipo_pagamento);
        numCartao = itemView.findViewById(R.id.info_cartao);
        nomeTitular = itemView.findViewById(R.id.info_titular);
        vencimento = itemView.findViewById(R.id.info_vencimento);
    }
}
