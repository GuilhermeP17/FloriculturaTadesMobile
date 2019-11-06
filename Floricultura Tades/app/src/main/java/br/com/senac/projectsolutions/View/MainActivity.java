package br.com.senac.projectsolutions.View;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.MainAdapter;
import br.com.senac.projectsolutions.Controller.MainController;
import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private SharedPreferences preferences;
    private SwipeRefreshLayout refreshAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        findViewsById();

        final MainController controller = new MainController();
        controller.getProdutosDisponiveis(MainActivity.this);

        preferences = getSharedPreferences("SessaoUsuario", MODE_PRIVATE);
        setDrawerLayout();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.btn_carrinho_compras){
                    Intent intent = new Intent(MainActivity.this, CarrinhoActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        refreshAction.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                controller.getProdutosDisponiveis(MainActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            MainActivity.super.finishAffinity();
        }
    }

    private void findViewsById() {
        refreshAction = findViewById(R.id.swipe_refresh);
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

            ConstraintLayout headerLogado = header.findViewById(R.id.header_logado);
            ConstraintLayout headerVisitante = header.findViewById(R.id.header_visitante);

            headerLogado.setVisibility(View.GONE);
            headerVisitante.setVisibility(View.VISIBLE);

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
        refreshAction.setRefreshing(false);
        if (!result) {
            produtos = new ArrayList<>();
            View parentView = findViewById(android.R.id.content);
            Snackbar.make(parentView, mensagem, Snackbar.LENGTH_LONG).show();
        }

        setProdutosAdapter(produtos);
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

        switch (item.getItemId()) {
            case R.id.visualizar_cadastro:
                if (preferences.getString("email", "").isEmpty()) {
                    intent = new Intent(this, LoginActivity.class);
                }else{
                    intent = new Intent(this, PerfilActivity.class);
                }
                break;
            case R.id.pedidos_andamento:
                if (preferences.getString("email", "").isEmpty()) {
                    intent = new Intent(this, LoginActivity.class);
                }else{
                    intent = new Intent(this, PedidosAndamentoActivity.class);
                }
                break;
            case R.id.pedidos_finalizados:
                if (preferences.getString("email", "").isEmpty()) {
                    intent = new Intent(this, LoginActivity.class);
                }else{
                    intent = new Intent(this, PedidosFinalizadosActivity.class);
                }
                break;
            case R.id.logout:
                if (!preferences.getString("email", "").isEmpty()) {
                    finalizarSessao();
                }else{
                    View parentView = findViewById(android.R.id.content);
                    Snackbar.make(parentView, "Não existe nenhuma sessão em andamento", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
        if (intent != null)
            startActivity(intent);

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void finalizarSessao(){
        new AlertDialog.Builder(this).setTitle("Finalizando sessão !!")
                .setMessage("Você está prestes a finalizar sua sessão no aplicativo, tem certeza que desja continuar ?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences = getSharedPreferences("SessaoUsuario", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("email", "");
                        editor.putString("cpf", "");
                        editor.putString("nome", "");
                        editor.putInt("codigo", 0);
                        editor.apply();

                        setHeaderView();
                    }
                }).show();
    }
}
