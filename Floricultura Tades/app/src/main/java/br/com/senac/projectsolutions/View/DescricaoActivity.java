package br.com.senac.projectsolutions.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import br.com.senac.projectsolutions.R;

public class DescricaoActivity extends AppCompatActivity {
    private CollapsingToolbarLayout toolbarManager;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextView nomeProduto, precoProduto, tipoProduto, descricaoProduto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao);
        findViewsById();
        final Intent intent = getIntent();
        checaCarrinho(intent);

        toolbarManager.setTitle(intent.getStringExtra("nomeProduto"));
        toolbarManager.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        toolbarManager.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));

        nomeProduto.setText(intent.getStringExtra("nomeProduto"));
        precoProduto.setText("R$ ".concat(String.format(Locale.US, "%.2f", intent.getDoubleExtra("precoProduto", 0)).replace(".", ",")));
        tipoProduto.setText("Tipo: ".concat(intent.getStringExtra("tipoProduto")));
        descricaoProduto.setText(intent.getStringExtra("descricaoProduto"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentView = findViewById(android.R.id.content);

                String retorno = salvarItemNoCarrinho(intent);
                Snackbar.make(parentView, retorno, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void findViewsById() {
        toolbar = findViewById(R.id.toolbar_descricao);
        toolbarManager = findViewById(R.id.collapsed_toolbar);
        fab = findViewById(R.id.add_carrinho);
        nomeProduto = findViewById(R.id.nome_produto);
        precoProduto = findViewById(R.id.preco_produto);
        tipoProduto = findViewById(R.id.tipo_produto);
        descricaoProduto = findViewById(R.id.descricao_produto);
    }

    private String salvarItemNoCarrinho(Intent intent) {
        SharedPreferences sharedPreferences = getSharedPreferences("ItensSalvos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String msgRetorno = "";

        if (sharedPreferences.getString(intent.getStringExtra("nomeProduto"), "").isEmpty()) {
            try {
                JSONObject json = new JSONObject();
                json.put("CodigoProduto", intent.getIntExtra("codigoProduto", 0));
                json.put("NomeProduto", intent.getStringExtra("nomeProduto"));
                json.put("DescricaoProduto", intent.getStringExtra("descricaoProduto"));
                json.put("PrecoProduto", intent.getDoubleExtra("precoProduto", 0));
                json.put("TipoProduto", intent.getStringExtra("tipoProduto"));

                editor.putString(intent.getStringExtra("nomeProduto"), json.toString());
                editor.apply();

                msgRetorno = "Produto adcionado ao Carrinho!";
                fab.setImageDrawable(getDrawable(R.drawable.ic_empty_cart));
            } catch (JSONException e) {
                e.printStackTrace();
                msgRetorno = "Falha ao adicionar ao carrinho!";
                return msgRetorno;
            }
        } else {
            editor.remove(intent.getStringExtra("nomeProduto"));
            editor.apply();

            fab.setImageDrawable(getDrawable(R.drawable.ic_shopping_cart));
            msgRetorno = "Produto removido do carrinho!";
        }

        return msgRetorno;
    }

    private void checaCarrinho(Intent intent){
        SharedPreferences sharedPreferences = getSharedPreferences("ItensSalvos", MODE_PRIVATE);

        if (sharedPreferences.getString(intent.getStringExtra("nomeProduto"), "").isEmpty()) {
            fab.setImageDrawable(getDrawable(R.drawable.ic_shopping_cart));
        }else{
            fab.setImageDrawable(getDrawable(R.drawable.ic_empty_cart));
        }
    }
}
