package br.com.senac.projectsolutions.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.R;

public class EnderecoAdapter extends RecyclerView.Adapter<EnderecoHolder> {
    private ArrayList<Endereco> enderecosUser;
    private Context context;

    public EnderecoAdapter(Context context, ArrayList<Endereco> enderecosUser){
        this.context = context;
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

        holder.editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarInfoEndereco(position);
            }
        });

        /*if (position == 0){
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
        });*/
    }

    @Override
    public int getItemCount() {
        return enderecosUser.size();
    }

    private void editarInfoEndereco(int position){
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogConfig = inflater.inflate(R.layout.alert_edit_endereco, null);

        TextInputEditText endereco = dialogConfig.findViewById(R.id.et_endereco);
        TextInputEditText numero = dialogConfig.findViewById(R.id.et_numero);
        TextInputEditText cep = dialogConfig.findViewById(R.id.et_cep);
        TextInputEditText complemento = dialogConfig.findViewById(R.id.et_complemento);
        AppCompatAutoCompleteTextView arrayEstados = dialogConfig.findViewById(R.id.spinner_estado);
        TextInputEditText cidade = dialogConfig.findViewById(R.id.et_cidade);
        TextInputEditText bairro = dialogConfig.findViewById(R.id.et_bairro);
        AppCompatAutoCompleteTextView arrayTipoEndereco = dialogConfig.findViewById(R.id.spinner_tipo_endereco);

        ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(context, R.array.estados, R.layout.support_simple_spinner_dropdown_item);
        arrayEstados.setAdapter(adapterEstados);

        ArrayAdapter<CharSequence> adapterEndereco = ArrayAdapter.createFromResource(context, R.array.tipo_enderecos, R.layout.support_simple_spinner_dropdown_item);
        arrayTipoEndereco.setAdapter(adapterEndereco);

        endereco.setText(enderecosUser.get(position).getLogradouro());
        numero.setText(String.valueOf(enderecosUser.get(position).getNumero()));
        cep.setText(enderecosUser.get(position).getCep());
        complemento.setText(enderecosUser.get(position).getComplemento());
        arrayEstados.setText(enderecosUser.get(position).getEstado());
        cidade.setText(enderecosUser.get(position).getCidade());
        bairro.setText(enderecosUser.get(position).getBairro());
        arrayTipoEndereco.setText(enderecosUser.get(position).getTipoEndereco());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialogConfig);
        builder.setTitle("Editar Endereço");
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
