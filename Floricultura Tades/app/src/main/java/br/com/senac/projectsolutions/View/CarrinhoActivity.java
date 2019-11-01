package br.com.senac.projectsolutions.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;

public class CarrinhoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        findViewsById();

        getItensAdcionados();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void findViewsById(){
        toolbar = findViewById(R.id.toolbar_carrinho);
    }

    private void getItensAdcionados(){
        sharedPreferences = getSharedPreferences("Carrinho de Compras", MODE_PRIVATE);

        try {
            JSONArray array = new JSONArray(sharedPreferences.getString("ItensSalvos", ""));
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
            setRecyclerView(produtosSalvos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setRecyclerView(ArrayList<Produto> produtos){

    }
}
