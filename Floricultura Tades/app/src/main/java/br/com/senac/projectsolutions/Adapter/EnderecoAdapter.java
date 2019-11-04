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

    public EnderecoAdapter(){
        enderecosUser = new ArrayList<>();
        for (int i = 0; i < 2 ; i ++){
            Endereco endereco = new Endereco();
            if (i == 0){
                endereco.setTipoEndereco("Entrega");
                endereco.setLogradouro("Rua Vicente Decara Neto");
                endereco.setNumero(77);
                endereco.setBairro("Jd. Santo Antonio");
                endereco.setEstado("SP");
                endereco.setCidade("São Paulo");
                endereco.setCep("05819-000");
            }else{
                endereco.setTipoEndereco("Cobrança");
                endereco.setLogradouro("Rua Philippe Di Vitry");
                endereco.setNumero(77);
                endereco.setBairro("Jd. Santo Antonio");
                endereco.setEstado("SP");
                endereco.setCidade("São Paulo");
                endereco.setCep("05819-000");
            }
            enderecosUser.add(endereco);
        }
    }

    @NonNull
    @Override
    public EnderecoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EnderecoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_endereco, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final EnderecoHolder holder, final int position) {
        holder.tipoEndereco.setText(("Endereço de".concat(enderecosUser.get(position).getTipoEndereco())));
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
        return 2;
    }
}
