package br.com.senac.projectsolutions.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import br.com.senac.projectsolutions.Adapter.PagamentosAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Pagamento;
import br.com.senac.projectsolutions.R;

public class PagamentoCarrinhoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvSubtotal, tvFrete, tvTotal;
    private RecyclerView pagamentosUsuario;
    private MaterialButton finalizarCompra;
    private ArrayList<Pagamento> listPagamentos;
    private ProgressBar loadingPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        findViewsById();
        loadingPage.setIndeterminate(true);
        final Bundle bundle = getIntent().getExtras();

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
                if (containPagamentos()) {
                    JSONObject jsonVenda = makeJsonVenda(bundle);
                    PerfilController controller = new PerfilController();
                    controller.postCadatrarVenda(PagamentoCarrinhoActivity.this, jsonVenda);
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor, cadastre um pagamento para continuar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onServidorResponse(boolean status, ArrayList<Pagamento> pagamentosUsuario, String metodo) {
        loadingPage.setVisibility(View.GONE);
        if (status) {
            if (metodo.equals("listagem_pagamentos")) {
                listPagamentos = pagamentosUsuario;
                setRecyclerView(pagamentosUsuario);
            } else {
                Toast.makeText(getApplicationContext(), "Venda cadastrada com sucesso", Toast.LENGTH_LONG).show();
                excluirCarrinho();
                Intent intent = new Intent(PagamentoCarrinhoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private void findViewsById() {
        toolbar = findViewById(R.id.toolbar_endereco);
        tvSubtotal = findViewById(R.id.preco_subtotal);
        tvFrete = findViewById(R.id.preco_frete);
        tvTotal = findViewById(R.id.preco_total);
        pagamentosUsuario = findViewById(R.id.recycler_pagamento);
        finalizarCompra = findViewById(R.id.btn_finalizar_compra);
        loadingPage = findViewById(R.id.loading_page);
    }

    private void setRecyclerView(ArrayList<Pagamento> pagamentosUsuario) {
        LinearLayoutManager linearManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.recycler_pagamento);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(linearManager);
        PagamentosAdapter adapter = new PagamentosAdapter(pagamentosUsuario, PagamentoCarrinhoActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private JSONObject makeJsonVenda(Bundle bundle) {
        JSONObject venda = new JSONObject();
        String subtotal = bundle.getString("subtotal").replace("R$", "").replace(",", ".");
        String frete = bundle.getString("frete").replace("R$", "").replace(",", ".");
        String total = bundle.getString("total").replace("R$", "").replace(",", ".");

        try {
            JSONObject produtosFromBundle = new JSONObject(bundle.getString("produtosCarrinho"));
            JSONArray produtos = produtosFromBundle.getJSONArray("produtos");

            venda.put("quantidadeItens", bundle.getInt("quantidadeTotal"));
            venda.put("valorFrete", frete);
            venda.put("valorTotal", total);
            venda.put("idEndereco", bundle.getInt("endereco"));
            venda.put("codigoUsuario", 2);
            venda.put("idStatus", 1);
            venda.put("idPagamento", bundlePagamento(listPagamentos));
            venda.put("produtos", produtos);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return venda;
    }

    private void excluirCarrinho() {
        SharedPreferences sharedPreferences = getSharedPreferences("ItensSalvos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Map<String, ?> produtosPreferences = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : produtosPreferences.entrySet()) {
            editor.remove(entry.getKey());
        }
        editor.apply();
    }

    private int bundlePagamento(ArrayList<Pagamento> pagamentos) {
        int codigoPagamento = 0;

        for (Pagamento pagamento : pagamentos) {
            codigoPagamento = pagamento.getId();
            break;
        }

        return codigoPagamento;
    }

    private boolean containPagamentos() {
        if(listPagamentos == null){
            listPagamentos = new ArrayList<>();
        }
        return listPagamentos.size() > 0;
    }
}
