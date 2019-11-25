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
import java.util.HashMap;

import br.com.senac.projectsolutions.Adapter.CarrinhoAdapter;
import br.com.senac.projectsolutions.Adapter.EnderecoAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.R;

public class EnderecosPerfilFragment extends Fragment {
    private ArrayList<Endereco> enderecos;
    private Context context;
    private RecyclerView recyclerView;
    private MaterialButton btnAddEndereco;
    private TextInputEditText endereco, numero, cep, complemento, cidade, bairro;
    private AppCompatAutoCompleteTextView arrayTipoEndereco, arrayEstados;


    public EnderecosPerfilFragment(ArrayList<Endereco> enderecos, Context context) {
        this.enderecos = enderecos;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enderecos_perfil, container, false);
        findViewsById(view);

        btnAddEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEndereco();
            }
        });

        setRecyclerView();
        return view;
    }

    private void findViewsById(View view) {
        recyclerView = view.findViewById(R.id.recycler_endereco);
        btnAddEndereco = view.findViewById(R.id.btn_adicionar_endereco);
    }

    private void setRecyclerView() {
        LinearLayoutManager gridManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(gridManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, gridManager.getOrientation()));
        EnderecoAdapter adapter = new EnderecoAdapter(context, enderecos);
        recyclerView.setAdapter(adapter);
    }

    private void addEndereco() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogConfig = inflater.inflate(R.layout.alert_edit_endereco, null);

         endereco = dialogConfig.findViewById(R.id.et_endereco);
         numero = dialogConfig.findViewById(R.id.et_numero);
         cep = dialogConfig.findViewById(R.id.et_cep);
         complemento = dialogConfig.findViewById(R.id.et_complemento);
         arrayEstados = dialogConfig.findViewById(R.id.spinner_estado);
         cidade = dialogConfig.findViewById(R.id.et_cidade);
         bairro = dialogConfig.findViewById(R.id.et_bairro);
         arrayTipoEndereco = dialogConfig.findViewById(R.id.spinner_tipo_endereco);

        ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(context, R.array.estados, R.layout.support_simple_spinner_dropdown_item);
        arrayEstados.setAdapter(adapterEstados);

        ArrayAdapter<CharSequence> adapterEndereco = ArrayAdapter.createFromResource(context, R.array.tipo_enderecos, R.layout.support_simple_spinner_dropdown_item);
        arrayTipoEndereco.setAdapter(adapterEndereco);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialogConfig);
        builder.setTitle("Adicionar Endereço");
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (validaCadstroEndereco()){
                    PerfilController controller = new PerfilController();
                    controller.postCadatrarEndereco(context, makeJsonEndereco());
                }else{
                    Toast.makeText(context, "Verifique os campos e tente novamente", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.create();
        builder.show();
    }

    private JSONObject makeJsonEndereco(){
        JSONObject jsonEndereco = new JSONObject();
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        try {
            jsonEndereco.put("logradouro", endereco.getText().toString());
            jsonEndereco.put("numero", numero.getText().toString());
            jsonEndereco.put("complemento", complemento.getText().toString());
            jsonEndereco.put("cep", cep.getText().toString());
            jsonEndereco.put("bairro", bairro.getText().toString());
            jsonEndereco.put("cidade", cidade.getText().toString());
            jsonEndereco.put("estado", arrayEstados.getText().toString());
            jsonEndereco.put("tipoEndereco", arrayTipoEndereco.getText().toString());
            jsonEndereco.put("codigoUsuario", sharedPreferences.getInt("codigo", 0));

        }catch (JSONException e){
            e.printStackTrace();
        }

        return jsonEndereco;
    }

    private boolean validaCadstroEndereco(){
        if (endereco.getText().toString().isEmpty()){
            return false;
        }
        if (numero.getText().toString().isEmpty()){
            return false;
        }
        if (cep.getText().toString().isEmpty()){
            return false;
        }
        if (complemento.getText().toString().isEmpty()){
            return false;
        }
        if (arrayEstados.getText().toString().isEmpty()){
            return false;
        }
        if (cidade.getText().toString().isEmpty()){
            return false;
        }
        if (arrayTipoEndereco.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
}