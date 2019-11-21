package br.com.senac.projectsolutions.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Pagamento;
import br.com.senac.projectsolutions.R;

public class PagamentosAdapter extends RecyclerView.Adapter<PagamentosHolder> {
    private ArrayList<Pagamento> pagamentosCadastrados;

    public PagamentosAdapter(ArrayList<Pagamento> pagamentosCadastrados){
        this.pagamentosCadastrados = pagamentosCadastrados;
    }

    @NonNull
    @Override
    public PagamentosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagamentosHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagamento, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PagamentosHolder holder, int position) {
        holder.tipoPagamento.setText(pagamentosCadastrados.get(position).getTipoPagamento());
        holder.numCartao.setText("****.****.****".concat(pagamentosCadastrados.get(position).getNumeroPagamento()));
        holder.nomeTitular.setText(pagamentosCadastrados.get(position).getNomeTitular());
        holder.vencimento.setText(pagamentosCadastrados.get(position).getDataVencimento());
    }

    @Override
    public int getItemCount() {
        return pagamentosCadastrados.size();
    }
}
