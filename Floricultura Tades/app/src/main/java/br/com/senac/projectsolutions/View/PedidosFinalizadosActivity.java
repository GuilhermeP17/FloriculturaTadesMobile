package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.PedidosAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Venda;
import br.com.senac.projectsolutions.R;

public class PedidosFinalizadosActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView pedidosFinalizados;
    private LinearLayout loadingScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_finalizados);
        findViewsById();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        PerfilController controller = new PerfilController();
        controller.getPedidosFinalizados(PedidosFinalizadosActivity.this);
    }

    private void findViewsById(){
        toolbar = findViewById(R.id.toolbar_finalizados);
        pedidosFinalizados = findViewById(R.id.recycler_andamento);
        loadingScreen = findViewById(R.id.linear_loading);
    }

    public void onServidorResponse(boolean status, ArrayList<Venda> pedidosFinalizados, String msg) {
        LinearLayout linearRecycler = findViewById(R.id.linear_recycler);
        LinearLayout linearEmpty = findViewById(R.id.linear_empty);
        loadingScreen.setVisibility(View.GONE);

        if(status){
            setRecyclerView(pedidosFinalizados);
            if (pedidosFinalizados.size() > 0){
                linearRecycler.setVisibility(View.VISIBLE);
                linearEmpty.setVisibility(View.GONE);
            }else{
                linearRecycler.setVisibility(View.GONE);
                linearEmpty.setVisibility(View.VISIBLE);
            }
        }else{
            linearRecycler.setVisibility(View.GONE);
            linearEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setRecyclerView(ArrayList<Venda> pedidosFinalizado){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        pedidosFinalizados.setLayoutManager(linearLayoutManager);
        pedidosFinalizados.addItemDecoration(new DividerItemDecoration(getApplicationContext(), linearLayoutManager.getOrientation()));
        PedidosAdapter adapter = new PedidosAdapter(pedidosFinalizado);
        pedidosFinalizados.setAdapter(adapter);
    }
}
