package br.com.senac.projectsolutions.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.MainAdapter;
import br.com.senac.projectsolutions.Controller.MainController;
import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        findViewsById();

        MainController controller = new MainController();
        controller.getProdutosDisponiveis(MainActivity.this);

        preferences = getSharedPreferences("SessaoUsuario", MODE_PRIVATE);
        setDrawerLayout();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.btn_carrinho_compras)
                    Toast.makeText(getApplicationContext(), "Teste carrinho", Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    private void findViewsById() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setDrawerLayout() {
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        drawerToggle.getDrawerArrowDrawable().
                setColor(getResources().
                        getColor(R.color.whiteFont));
        NavigationView navMain = findViewById(R.id.nav_view);
        navMain.setNavigationItemSelectedListener(this);
        setHeaderView();
    }

    private void setHeaderView() {
        if (preferences.getString("email", "").isEmpty()) {
            View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
            ConstraintLayout headerVisitante = header.findViewById(R.id.header_visitante);
            headerVisitante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
            ConstraintLayout headerLogado = header.findViewById(R.id.header_logado);
            ConstraintLayout headerVisitante = header.findViewById(R.id.header_visitante);
            TextView nomeUser = header.findViewById(R.id.nome_usuario);
            TextView emailUser = header.findViewById(R.id.email_usuario);
            headerLogado.setVisibility(View.VISIBLE);
            headerVisitante.setVisibility(View.GONE);
            nomeUser.setText(preferences.getString("nome", ""));
            emailUser.setText(preferences.getString("email", ""));
        }
    }

    public void onServidorResponse(boolean result, String mensagem, ArrayList<Produto> produtos) {
        if (result) {
            setProdutosAdapter(produtos);
        } else {
            Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
        }
    }

    private void setProdutosAdapter(ArrayList<Produto> produtos) {
        GridLayoutManager gridManager = new GridLayoutManager(getApplicationContext(), 2);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_main);
        recyclerView.setLayoutManager(gridManager);
        MainAdapter adapter = new MainAdapter(getApplicationContext(), produtos);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        String mensagem = "";
        if (preferences.getString("email", "").isEmpty()) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            switch (item.getItemId()) {
                case R.id.visualizar_cadastro:
                    mensagem = "Visualizar cadastro";
                    break;
                case R.id.pedidos_andamento:
                    mensagem = "Pedidos em andamento";
                    break;
                case R.id.pedidos_finalizados:
                    mensagem = "Pedidos Finalizados";
                    break;
            }
        }
        Toast.makeText(MainActivity.this, mensagem, Toast.LENGTH_SHORT).show();
        if (intent != null)
            startActivity(intent);

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
