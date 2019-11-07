package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.AbasPerfilAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.Model.Produto;
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
        controller.getInfoEndereco(PerfilActivity.this, "endereco_perfil");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onServidorResponse(boolean status, ArrayList<Endereco> enderecos, String mensagem){
        if (status){
            setAbasAdapter(enderecos);
        }else{
            Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
        }
    }

    private void setAbasAdapter(ArrayList<Endereco> enderecos){
        AbasPerfilAdapter abas = new AbasPerfilAdapter(getSupportFragmentManager());
        abas.adicionar(new DadosPerfilFragment(PerfilActivity.this), "Meus Dados");
        abas.adicionar(new EnderecosPerfilFragment(enderecos, PerfilActivity.this), "Meus Endereços");

        viewPager.setAdapter(abas);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void findViewsById(){
        toolbar = findViewById(R.id.toolbar_perfil);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
    }
}
