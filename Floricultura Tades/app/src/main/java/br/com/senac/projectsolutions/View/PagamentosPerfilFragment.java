package br.com.senac.projectsolutions.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.EnderecoAdapter;
import br.com.senac.projectsolutions.Adapter.PagamentosAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Pagamento;
import br.com.senac.projectsolutions.R;

public class PagamentosPerfilFragment extends Fragment {
    private Context context;
    private ArrayList<Pagamento> pagamentos;
    private RecyclerView recyclerView;
    private MaterialButton btnAddPagamento;
    private TextInputEditText numPagamento, nomeTitular, dataVencimento, codigoSeguranca;
    private AppCompatAutoCompleteTextView arrayTipoPagamento;

    public PagamentosPerfilFragment(ArrayList<Pagamento> pagamentos, Context context) {
        this.context = context;
        this.pagamentos = pagamentos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagamento_perfil, container, false);
        findViewsById(view);

        btnAddPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPagamento();
            }
        });

        setRecyclerView();
        return view;
    }

    private void findViewsById(View view) {
        recyclerView = view.findViewById(R.id.recycler_pagamento);
        btnAddPagamento = view.findViewById(R.id.btn_adicionar_endereco);
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, linearLayoutManager.getOrientation()));
        PagamentosAdapter adapter = new PagamentosAdapter(pagamentos, context);
        recyclerView.setAdapter(adapter);
    }

    private void addPagamento() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogConfig = inflater.inflate(R.layout.alert_edit_pagamento, null);

        arrayTipoPagamento = dialogConfig.findViewById(R.id.spinner_tipo_pagamento);
        numPagamento = dialogConfig.findViewById(R.id.et_num_cartao);
        nomeTitular = dialogConfig.findViewById(R.id.et_nome_titular);
        dataVencimento = dialogConfig.findViewById(R.id.et_dt_vencimento);
        codigoSeguranca = dialogConfig.findViewById(R.id.et_codigo_segrunanca);

        ArrayAdapter<CharSequence> adapterEndereco = ArrayAdapter.createFromResource(context, R.array.tipo_pagamento, R.layout.support_simple_spinner_dropdown_item);
        arrayTipoPagamento.setAdapter(adapterEndereco);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialogConfig);
        builder.setTitle("Adicionar Pagamento");
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (validaCadstroPagamento()) {
                    PerfilController controller = new PerfilController();
                    controller.postCadatrarPagamento(context, makeJsonEndereco());
                } else {
                    Toast.makeText(context, "Verifique os campos e tente novamente", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.create();
        builder.show();
    }

    private JSONObject makeJsonEndereco() {
        JSONObject jsonEndereco = new JSONObject();
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        try {
            jsonEndereco.put("tipoPagamento", arrayTipoPagamento.getText().toString());
            jsonEndereco.put("numeroPagamento", numPagamento.getText().toString());
            jsonEndereco.put("nomeTitular", nomeTitular.getText().toString());
            jsonEndereco.put("dataVencimento", dataVencimento.getText().toString());
            jsonEndereco.put("codigoSegurança", codigoSeguranca.getText().toString());
            jsonEndereco.put("idUsuario", sharedPreferences.getInt("codigo", 0));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonEndereco;
    }

    private boolean validaCadstroPagamento(){
        if (arrayTipoPagamento.getText().toString().isEmpty()){
            return false;
        }
        if (numPagamento.getText().toString().isEmpty()){
            return false;
        }
        if (nomeTitular.getText().toString().isEmpty()){
            return false;
        }
        if (dataVencimento.getText().toString().isEmpty()){
            return false;
        }
        if (codigoSeguranca.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
}
