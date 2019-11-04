
package br.com.senac.projectsolutions.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import br.com.senac.projectsolutions.Adapter.CarrinhoAdapter;
import br.com.senac.projectsolutions.Adapter.MainAdapter;
import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CarrinhoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private MaterialButton btnProsseguir;
    public  TextView subTotal, frete, total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        findViewsById();

        getItensAdcionados();

        btnProsseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("SessaoUsuario", MODE_PRIVATE);
                Intent intent;
                if(sharedPreferences.getString("email", "").isEmpty()){
                    intent = new Intent(CarrinhoActivity.this, LoginActivity.class);
                }else{
                    intent = new Intent(CarrinhoActivity.this, EnderecoActivity.class);
                }
                startActivity(intent);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void findViewsById() {
        toolbar = findViewById(R.id.toolbar_carrinho);
        subTotal = findViewById(R.id.preco_subtotal);
        frete = findViewById(R.id.preco_frete);
        total = findViewById(R.id.preco_total);
        btnProsseguir = findViewById(R.id.btn_prosseguir_compra);
    }

    private void getItensAdcionados() {
        sharedPreferences = getSharedPreferences("ItensSalvos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nome", "Teste");
        editor.apply();

        if (!sharedPreferences.getString("nome", "").equals("")) {
            LinearLayout notEmptyView = findViewById(R.id.linear_not_empty);
            ConstraintLayout emptyView = findViewById(R.id.linear_empty);
            emptyView.setVisibility(GONE);
            notEmptyView.setVisibility(VISIBLE);

            setRecyclerView();
        } else {
            LinearLayout notEmptyView = findViewById(R.id.linear_not_empty);
            ConstraintLayout emptyView = findViewById(R.id.linear_empty);
            emptyView.setVisibility(VISIBLE);
            notEmptyView.setVisibility(GONE);
        }

        //Rotina para recuperar objetos salvos no Shared preferences de itens add no carrinho
            /*JSONArray array = new JSONArray(sharedPreferences.getString("nome", ""));
            ArrayList<Produto> produtosSalvos = new ArrayList<>();
            Produto produto;
            JSONObject json;
            for (int i = 0; i < array.length(); i++){
                json = array.getJSONObject(i);
                produto = new Produto();
                produto.setCodigo(json.getInt("codigo"));
                produto.setNome(json.getString("nome"));
                produto.setValor(json.getDouble("valor"));
                produto.setTipo(json.getString("tipo"));

                produtosSalvos.add(produto);
            }'
            setRecyclerView(produtosSalvos);*/
    }

    public void atualizaValorCompra(ArrayList<Produto> produtosCarrinho){
        double newSubTotal = 0;
        double frete = Double.parseDouble(this.frete.getText().toString().replace("R$ ", "").replace(",", "."));
        double total = 0;

        for (Produto prod : produtosCarrinho){
            newSubTotal += prod.getValor();
            subTotal.setText("R$ ".concat(String.format(Locale.US, "%.2f", newSubTotal).replace(".", ",")));
        }
        total += newSubTotal + frete;
        this.total.setText("R$ ".concat(String.format(Locale.US, "%.2f", total).replace(".", ",")));
    }

    private void setRecyclerView(/*ArrayList<Produto> produtos*/) {
        LinearLayoutManager gridManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.recycler_carrinho_itens);
        recyclerView.setLayoutManager(gridManager);
        CarrinhoAdapter adapter = new CarrinhoAdapter(CarrinhoActivity.this);
        recyclerView.setAdapter(adapter);
    }
}
