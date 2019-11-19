
package br.com.senac.projectsolutions.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import br.com.senac.projectsolutions.Adapter.CarrinhoAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CarrinhoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private MaterialButton btnProsseguir, btnAplicarCEP;
    public TextView subTotal, frete, total, cep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        findViewsById();

        getItensAdicionados();

        btnProsseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("SessaoUsuario", MODE_PRIVATE);
                Intent intent;
                if (sharedPreferences.getString("email", "").isEmpty()) {
                    intent = new Intent(CarrinhoActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(CarrinhoActivity.this, EnderecoCarrinhoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("subtotal", subTotal.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAplicarCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (cep.getText().toString().isEmpty()) {
                    cep.requestFocus();
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else {
                    atualizaValorCompra(null, 25.00);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
    }

    private void findViewsById() {
        toolbar = findViewById(R.id.toolbar_carrinho);
        subTotal = findViewById(R.id.preco_subtotal);
        frete = findViewById(R.id.preco_frete);
        total = findViewById(R.id.preco_total);
        cep = findViewById(R.id.et_cep_frete);
        btnAplicarCEP = findViewById(R.id.btn_aplicar);
        btnProsseguir = findViewById(R.id.btn_prosseguir_compra);
    }

    private void getItensAdicionados() {
        sharedPreferences = getSharedPreferences("ItensSalvos", MODE_PRIVATE);

        if (!sharedPreferences.getAll().isEmpty()) {
            LinearLayout notEmptyView = findViewById(R.id.linear_not_empty);
            ConstraintLayout emptyView = findViewById(R.id.linear_empty);
            emptyView.setVisibility(GONE);
            notEmptyView.setVisibility(VISIBLE);

//            Rotina para recuperar objetos salvos no Shared preferences de itens add no carrinho
            ArrayList<Produto> produtosSalvos = new ArrayList<>();
            JSONArray array = new JSONArray();

            try {
                Map<String, ?> produtosPreferences = sharedPreferences.getAll();
                for (Map.Entry<String, ?> entry : produtosPreferences.entrySet()) {
                    array.put(entry.getValue().toString());
                }
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = new JSONObject(array.getString(i));
                    Produto produto = new Produto();

                    produto.setCodigo(json.getInt("CodigoProduto"));
                    produto.setNome(json.getString("NomeProduto"));
                    produto.setDescricao(json.getString("DescricaoProduto"));
                    produto.setValor(json.getDouble("PrecoProduto"));
                    produto.setTipo(json.getString("TipoProduto"));

                    produtosSalvos.add(produto);
                }
                setRecyclerView(produtosSalvos);
                atualizaValorCompra(produtosSalvos, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            LinearLayout notEmptyView = findViewById(R.id.linear_not_empty);
            ConstraintLayout emptyView = findViewById(R.id.linear_empty);
            emptyView.setVisibility(VISIBLE);
            notEmptyView.setVisibility(GONE);
        }
    }

    public void atualizaValorCompra(ArrayList<Produto> produtosCarrinho, Double newFrete) {
        LinearLayout notEmptyView = findViewById(R.id.linear_not_empty);
        ConstraintLayout emptyView = findViewById(R.id.linear_empty);
        sharedPreferences = getSharedPreferences("ItensSalvos", MODE_PRIVATE);

        if (!sharedPreferences.getAll().isEmpty()) {
            emptyView.setVisibility(GONE);
            notEmptyView.setVisibility(VISIBLE);
        } else {
            emptyView.setVisibility(VISIBLE);
            notEmptyView.setVisibility(GONE);
            double newSubTotal = 0;
            double frete = Double.parseDouble(this.frete.getText().toString().replace("R$ ", "").replace(",", "."));
            double total = 0;

            if (produtosCarrinho != null) {
                for (Produto prod : produtosCarrinho) {
                    newSubTotal += prod.getValor();
                }
                subTotal.setText("R$ ".concat(String.format(Locale.US, "%.2f", newSubTotal).replace(".", ",")));
                total += newSubTotal + frete;
            } else {
                this.frete.setText("R$ ".concat(String.format(Locale.US, "%.2f", newFrete).replace(".", ",")));
                total = newFrete + Double.parseDouble(subTotal.getText().toString().replace("R$ ", "").replace(",", "."));
            }

            this.total.setText("R$ ".concat(String.format(Locale.US, "%.2f", total).replace(".", ",")));
        }
    }

    private void setRecyclerView(ArrayList<Produto> produtos) {
        LinearLayoutManager gridManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.recycler_carrinho_itens);
        recyclerView.setLayoutManager(gridManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), gridManager.getOrientation()));
        CarrinhoAdapter adapter = new CarrinhoAdapter(CarrinhoActivity.this, produtos);
        recyclerView.setAdapter(adapter);
    }
}
