package br.com.senac.projectsolutions.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.R;

public class DadosPerfilFragment extends Fragment {
    private Context context;
    private MaterialButton btnAlteraDados;
    private TextView nomeUsuario, emailUsuario, cpfUsuario;
    private TextInputEditText nome, email, cpf, senha, confirmarSenha;

    public DadosPerfilFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dados_perfil, container, false);
        findViewsById(view);

        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);
        int codigoUser = sharedPreferences.getInt("codigo", 0);
        nomeUsuario.setText(sharedPreferences.getString("nome", ""));
        emailUsuario.setText(sharedPreferences.getString("email", ""));
        cpfUsuario.setText(sharedPreferences.getString("cpf", ""));

        btnAlteraDados.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editarPerfil();
            }
        });

        return view;
    }

    private void findViewsById(View view){
        nomeUsuario = view.findViewById(R.id.content_nome);
        emailUsuario = view.findViewById(R.id.content_email);
        cpfUsuario = view.findViewById(R.id.content_cpf);
        btnAlteraDados = view.findViewById(R.id.btn_alterar_dados_cadastrais);
    }

    private void editarPerfil() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogConfig = inflater.inflate(R.layout.alert_edit_perfil, null);
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        nome = dialogConfig.findViewById(R.id.et_nome);
        email = dialogConfig.findViewById(R.id.et_email);
        cpf = dialogConfig.findViewById(R.id.et_cpf);
        senha = dialogConfig.findViewById(R.id.et_senha);
        confirmarSenha = dialogConfig.findViewById(R.id.et_confirma_senha);

        nome.setText(sharedPreferences.getString("nome", ""));
        email.setText(sharedPreferences.getString("email", ""));
        cpf.setText(sharedPreferences.getString("cpf", ""));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialogConfig);
        builder.setTitle("Alterar Cadastro");
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (validaCadastroPerfil()) {
                    PerfilController controller = new PerfilController();
                    controller.postAtualizarCadastro(context, makeJsonPerfil());
                } else {
                      Toast.makeText(context, "Verifique os campos e tente novamente", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.create();
        builder.show();
    }

    private JSONObject makeJsonPerfil(){
        JSONObject jsonPerfil = new JSONObject();
        SharedPreferences sharedPreferences = context.getSharedPreferences("SessaoUsuario", Context.MODE_PRIVATE);

        try {
            jsonPerfil.put("nome", nome.getText(). toString());
            jsonPerfil.put("email", email.getText().toString());
            jsonPerfil.put("CPF", cpf.getText().toString());
            jsonPerfil.put("senha", senha.getText().toString());
            jsonPerfil.put("confirmarSenha", confirmarSenha.getText().toString());
            jsonPerfil.put("codigo", sharedPreferences.getInt("codigo", 0));
        }catch (JSONException e){
            e.printStackTrace();
        }

        return jsonPerfil;
    }

    private boolean validaCadastroPerfil(){
        if (nome.getText().toString().isEmpty()){
            return false;
        }
        if (email.getText().toString().isEmpty()){
            return false;
        }
        if (cpf.getText().toString().isEmpty()){
            return false;
        }
        if (!senha.getText().toString().isEmpty() && confirmarSenha.getText().toString().isEmpty()){
            return false;
        }
        if (!confirmarSenha.getText().toString().isEmpty()){
            if (!senha.getText().toString().isEmpty() && !senha.getText().toString().equals(confirmarSenha.getText().toString())) {
                return false;
            }
        }

        return true;
    }
}
