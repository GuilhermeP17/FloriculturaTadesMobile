package br.com.senac.projectsolutions.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

import br.com.senac.projectsolutions.Adapter.EnderecoAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.R;

public class EnderecoCarrinhoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MaterialButton btnGoPagamentos;
    private TextView tvSubtotal, tvFrete, tvTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        findViewsById();
        Bundle bundle = getIntent().getExtras();

        atualizaValorCarrinho(bundle);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnGoPagamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnderecoCarrinhoActivity.this, PagamentoCarrinhoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("subtotal", tvSubtotal.getText().toString());
                bundle.putString("frete", tvFrete.getText().toString());
                bundle.putString("total", tvTotal.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        PerfilController controller = new PerfilController();
        controller.getInfoEndereco(EnderecoCarrinhoActivity.this, "endereco_carrinho");
    }

    public void onServidorResponse(boolean status, ArrayList<Endereco> enderecos, String msg) {
        if (status) {
            setRecyclerView(enderecos);
        } else {
            View parentView = findViewById(android.R.id.content);
            Snackbar.make(parentView, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    private void findViewsById() {
        toolbar = findViewById(R.id.toolbar_endereco);
        tvSubtotal = findViewById(R.id.preco_subtotal);
        tvFrete = findViewById(R.id.preco_frete);
        tvTotal = findViewById(R.id.preco_total);
        btnGoPagamentos = findViewById(R.id.btn_prosseguir_compra_02);
    }

    private void setRecyclerView(ArrayList<Endereco> enderecos) {
        LinearLayoutManager linearManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.recycler_endereco);
        recyclerView.setLayoutManager(linearManager);
        EnderecoAdapter adapter = new EnderecoAdapter(enderecos);
        recyclerView.setAdapter(adapter);
    }

    private void atualizaValorCarrinho(Bundle bundle) {
        double subtotal = Double.parseDouble(bundle.getString("subtotal").replace("R$ ", "").replace(",", "."));
        double valorFrete = 15.00;
        tvSubtotal.setText("R$".concat(String.format(Locale.US, "%.2f", subtotal)));
        tvFrete.setText("R$".concat(String.format(Locale.US, "%.2f", valorFrete).replace(".", ",")));
        tvTotal.setText("R$".concat(String.format(Locale.US, "%.2f", subtotal + valorFrete).replace(".", ",")));
    }

}
