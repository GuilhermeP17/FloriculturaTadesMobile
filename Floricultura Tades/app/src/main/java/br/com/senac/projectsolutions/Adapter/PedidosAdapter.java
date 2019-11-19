package br.com.senac.projectsolutions.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Venda;
import br.com.senac.projectsolutions.R;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosHolder>{
    private ArrayList<Venda> pedidos;

    public PedidosAdapter(ArrayList<Venda> pedidos){
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public PedidosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PedidosHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosHolder holder, int position) {
        holder.data.setText(pedidos.get(position).getData());
        holder.codigoVenda.setText(pedidos.get(position).getCodigoVenda());
        holder.quantidade.setText(String.valueOf(pedidos.get(position).getQuantidadeItens()));
        holder.valorTotal.setText(String.valueOf(pedidos.get(position).getValorTotal()));
        holder.statusPedido.setText(pedidos.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }
}
