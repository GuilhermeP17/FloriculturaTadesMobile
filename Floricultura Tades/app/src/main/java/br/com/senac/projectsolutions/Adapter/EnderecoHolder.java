package br.com.senac.projectsolutions.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import br.com.senac.projectsolutions.R;

public class EnderecoHolder extends RecyclerView.ViewHolder {
    protected TextView tipoEndereco, logradouro, adicionaisInfo, cep;
    protected LinearLayout enderecoPadrao;
    protected ConstraintLayout enderecoView;
    protected ImageView editinfo;

    public EnderecoHolder(@NonNull View itemView) {
        super(itemView);
        enderecoView = itemView.findViewById(R.id.card_selected);
        enderecoPadrao = itemView.findViewById(R.id.info_selected);
        tipoEndereco = itemView.findViewById(R.id.tipo_endereco);
        logradouro = itemView.findViewById(R.id.logradouro_info);
        adicionaisInfo = itemView.findViewById(R.id.infos_adicionais_info);
        cep = itemView.findViewById(R.id.cep_info);
        editinfo = itemView.findViewById(R.id.btn_edit);
    }
}
