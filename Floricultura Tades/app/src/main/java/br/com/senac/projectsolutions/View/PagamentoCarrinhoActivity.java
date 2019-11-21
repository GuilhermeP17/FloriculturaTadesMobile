package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.EnderecoAdapter;
import br.com.senac.projectsolutions.Adapter.PagamentosAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.Model.Pagamento;
import br.com.senac.projectsolutions.R;

public class PagamentoCarrinhoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvSubtotal, tvFrete, tvTotal;
    private RecyclerView pagamentosUsuario;
    private MaterialButton finalizarCompra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        findViewsById();
        Bundle bundle = getIntent().getExtras();

        tvSubtotal.setText(bundle.getString("subtotal"));
        tvFrete.setText(bundle.getString("frete"));
        tvTotal.setText(bundle.getString("total"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        PerfilController controller = new PerfilController();
        controller.getPagamentos(PagamentoCarrinhoActivity.this, "pagamento_carrinho");

        finalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void onServidorResponse(boolean status, ArrayList<Pagamento> pagamentosUsuario){
        if(status){
            setRecyclerView(pagamentosUsuario);
        }else{

        }
    }

    private void findViewsById() {
        toolbar = findViewById(R.id.toolbar_endereco);
        tvSubtotal = findViewById(R.id.preco_subtotal);
        tvFrete = findViewById(R.id.preco_frete);
        tvTotal = findViewById(R.id.preco_total);
        pagamentosUsuario = findViewById(R.id.recycler_pagamento);
        finalizarCompra = findViewById(R.id.btn_finalizar_compra);
    }

    private void setRecyclerView(ArrayList<Pagamento> pagamentosUsuario) {
        LinearLayoutManager linearManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.recycler_pagamento);
        recyclerView.setLayoutManager(linearManager);
        PagamentosAdapter adapter = new PagamentosAdapter(pagamentosUsuario);
        recyclerView.setAdapter(adapter);
    }
}
