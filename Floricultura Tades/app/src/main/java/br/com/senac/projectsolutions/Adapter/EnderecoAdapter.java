package br.com.senac.projectsolutions.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.R;

public class EnderecoAdapter extends RecyclerView.Adapter<EnderecoHolder> {
    private ArrayList<Endereco> enderecosUser;

    public EnderecoAdapter(ArrayList<Endereco> enderecosUser){
        this.enderecosUser = enderecosUser;
    }

    @NonNull
    @Override
    public EnderecoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EnderecoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_endereco, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final EnderecoHolder holder, final int position) {
        holder.tipoEndereco.setText(("Endereço de ".concat(enderecosUser.get(position).getTipoEndereco())));
        holder.logradouro.setText(enderecosUser.get(position).getLogradouro().concat(", " + enderecosUser.get(position).getNumero()));
        holder.adicionaisInfo.setText(enderecosUser.get(position).getBairro()
                .concat(", " + enderecosUser.get(position).getCidade() + " - " + enderecosUser.get(position).getEstado()));
        holder.cep.setText("CEP: ".concat(enderecosUser.get(position).getCep()));
        if (position == 0){
            holder.enderecoPadrao.setVisibility(View.VISIBLE);
        }

        holder.enderecoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (holder.enderecoPadrao.getVisibility() == View.VISIBLE){
                   holder.enderecoPadrao.setVisibility(View.GONE);
               }else{
                   holder.enderecoPadrao.setVisibility(View.VISIBLE);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return enderecosUser.size();
    }
}
