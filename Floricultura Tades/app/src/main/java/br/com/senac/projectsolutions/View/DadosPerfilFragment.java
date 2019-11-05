package br.com.senac.projectsolutions.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import br.com.senac.projectsolutions.R;

public class DadosPerfilFragment extends Fragment {
    private Context context;
    private MaterialButton btnAlteraDados;
    private TextView nomeUsuario, emailUsuario, cpfUsuario;

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

}
