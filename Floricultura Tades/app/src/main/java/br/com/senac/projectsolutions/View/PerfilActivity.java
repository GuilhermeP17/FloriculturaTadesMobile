package br.com.senac.projectsolutions.View;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.AbasPerfilAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.Model.Pagamento;
import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.Model.Usuario;
import br.com.senac.projectsolutions.R;

public class PerfilActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        findViewsById();

        PerfilController controller = new PerfilController();
        controller.getInfoPerfil(PerfilActivity.this, "endereco_perfil");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onServidorResponse(boolean status, Usuario user, ArrayList<Endereco> enderecos, ArrayList<Pagamento> pagamentos, String metodo){
        if (status){
            if (metodo.equals("atualizar_cadastro")){
                updateSharedPreferencesUsuario(user);
            }
            setAbasAdapter(enderecos, pagamentos);
        }else{
            enderecos = new ArrayList<>();
            setAbasAdapter(enderecos, pagamentos);
        }
    }

    private void updateSharedPreferencesUsuario(Usuario usuario) {
        SharedPreferences preferences = getSharedPreferences("SessaoUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", usuario.getEmail());
        editor.putString("cpf", usuario.getCpf());
        editor.putString("nome", usuario.getNome());
        editor.putInt("codigo", usuario.getCodigo());
        editor.apply();
    }

    private void setAbasAdapter(ArrayList<Endereco> enderecos, ArrayList<Pagamento> pagamentos){
        AbasPerfilAdapter abas = new AbasPerfilAdapter(getSupportFragmentManager());
        abas.adicionar(new DadosPerfilFragment(PerfilActivity.this), "Meus Dados");
        abas.adicionar(new EnderecosPerfilFragment(enderecos, PerfilActivity.this), "Meus Endereços");
        abas.adicionar(new PagamentosPerfilFragment(pagamentos, PerfilActivity.this), "Meus Pagamentos");
        abas.notifyDataSetChanged();

        viewPager.setAdapter(abas);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void findViewsById(){
        toolbar = findViewById(R.id.toolbar_perfil);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
    }
}
