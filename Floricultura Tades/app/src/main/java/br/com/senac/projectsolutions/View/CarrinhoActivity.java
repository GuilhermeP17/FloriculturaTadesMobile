
package br.com.senac.projectsolutions.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CarrinhoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private MaterialButton add01, add02, add03, remove01, remove02, remove03;
    private TextView quantidade01, quantidade02, quantidade03;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        findViewsById();

        getItensAdcionados();
        
        //// TODO: 03/11/2019 Remover apóes apresentacao 
        //Click Listener para os itens estáticos na tela
        add01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeManager(true, quantidade01);
            }
        });

        add02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeManager(true, quantidade02);
            }
        });

        add03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeManager(true, quantidade03);
            }
        });

        remove01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeManager(false, quantidade01);
            }
        });

        remove02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeManager(false, quantidade02);
            }
        });

        remove03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeManager(false, quantidade03);
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
        add01 = findViewById(R.id.btn_add_01);
        add02 = findViewById(R.id.btn_add_02);
        add03 = findViewById(R.id.btn_add_03);
        remove01 = findViewById(R.id.btn_remove_01);
        remove02 = findViewById(R.id.btn_remove_02);
        remove03 = findViewById(R.id.btn_remove_03);
        quantidade01 = findViewById(R.id.quantidade_01);
        quantidade02 = findViewById(R.id.quantidade_02);
        quantidade03 = findViewById(R.id.quantidade_03);

    }

    private void quantidadeManager(boolean adicionando, TextView quantidade) {
        if (adicionando){
            int qtdAtual = Integer.parseInt(quantidade.getText().toString());
            quantidade.setText(String.valueOf(qtdAtual + 1));
        }else{
            int qtdAtual = Integer.parseInt(quantidade.getText().toString());
            if (qtdAtual > 1){
                quantidade.setText(String.valueOf(qtdAtual - 1));
            }
        }
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
            }
            setRecyclerView(produtosSalvos);*/

    }

    private void setRecyclerView(ArrayList<Produto> produtos) {

    }
}
