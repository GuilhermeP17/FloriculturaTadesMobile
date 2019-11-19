package br.com.senac.projectsolutions.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.senac.projectsolutions.R;

public class PedidosHolder extends RecyclerView.ViewHolder  {

    public TextView data, codigoVenda, quantidade, valorTotal, statusPedido;

    public PedidosHolder(@NonNull View itemView) {
        super(itemView);
        data = itemView.findViewById(R.id.data_produto);
        codigoVenda = itemView.findViewById(R.id.tv_codigo);
        quantidade = itemView.findViewById(R.id.tv_qtd);
        valorTotal = itemView.findViewById(R.id.tv_total);
        statusPedido = itemView.findViewById(R.id.tv_status);
    }
}
