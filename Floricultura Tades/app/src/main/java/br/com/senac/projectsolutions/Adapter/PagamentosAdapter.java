package br.com.senac.projectsolutions.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Pagamento;
import br.com.senac.projectsolutions.R;

public class PagamentosAdapter extends RecyclerView.Adapter<PagamentosHolder> {
    private ArrayList<Pagamento> pagamentosCadastrados;
    private Context context;

    public PagamentosAdapter(ArrayList<Pagamento> pagamentosCadastrados, Context context){
        this.pagamentosCadastrados = pagamentosCadastrados;
        this.context = context;
    }

    @NonNull
    @Override
    public PagamentosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagamentosHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagamento, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PagamentosHolder holder, final int position) {
        holder.tipoPagamento.setText(pagamentosCadastrados.get(position).getTipoPagamento());
        holder.numCartao.setText("****.****.****.".concat(pagamentosCadastrados.get(position).getNumeroPagamento()));
        holder.nomeTitular.setText(pagamentosCadastrados.get(position).getNomeTitular());
        holder.vencimento.setText(pagamentosCadastrados.get(position).getDataVencimento());

        holder.btnEditPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarInfoEndereco(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pagamentosCadastrados.size();
    }

    private void editarInfoEndereco(int position){
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogConfig = inflater.inflate(R.layout.alert_edit_pagamento, null);

        AppCompatAutoCompleteTextView arrayTipoPagamento = dialogConfig.findViewById(R.id.spinner_tipo_pagamento);
        TextInputEditText numPagamento = dialogConfig.findViewById(R.id.et_num_cartao);
        TextInputEditText nomeTitular = dialogConfig.findViewById(R.id.et_nome_titular);
        TextInputEditText dataVencimento = dialogConfig.findViewById(R.id.et_dt_vencimento);
        TextInputEditText codigoSeguranca = dialogConfig.findViewById(R.id.et_codigo_segrunanca);

        ArrayAdapter<CharSequence> adapterEndereco = ArrayAdapter.createFromResource(context, R.array.tipo_pagamento, R.layout.support_simple_spinner_dropdown_item);
        arrayTipoPagamento.setAdapter(adapterEndereco);

        arrayTipoPagamento.setText(pagamentosCadastrados.get(position).getTipoPagamento().replace("Cartão de ", ""));
        numPagamento.setText("****.****.****.".concat(pagamentosCadastrados.get(position).getNumeroPagamento()));
        nomeTitular.setText(pagamentosCadastrados.get(position).getNomeTitular());
        dataVencimento.setText(pagamentosCadastrados.get(position).getDataVencimento());
        codigoSeguranca.setText(pagamentosCadastrados.get(position).getCodigoSegurança());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialogConfig);
        builder.setTitle("Editar Pagamento");
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }
}
