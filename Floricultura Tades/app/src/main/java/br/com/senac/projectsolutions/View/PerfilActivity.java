package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import br.com.senac.projectsolutions.Adapter.AbasPerfilAdapter;
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AbasPerfilAdapter abas = new AbasPerfilAdapter(getSupportFragmentManager());
        abas.adicionar(new DadosPerfilFragment(PerfilActivity.this), "Meus Dados");
        abas.adicionar(new EnderecosPerfilFragment(), "Meus Endereços");

        viewPager.setAdapter(abas);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void findViewsById(){
        toolbar = findViewById(R.id.toolbar_perfil);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
    }
}
