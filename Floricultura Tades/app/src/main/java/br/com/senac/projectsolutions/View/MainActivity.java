package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        findViewsById();
        setDrawerLayout();

        MainController controller = new MainController();
        controller.getProdutosDisponiveis(MainActivity.this);
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
        navMain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.visualizar_cadastro:
                        Toast.makeText(MainActivity.this, "Teste", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.pedidos_andamento:
                        Toast.makeText(MainActivity.this, "Teste", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.pedidos_finalizados:
                        Toast.makeText(MainActivity.this, "Teste", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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
}
