package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.senac.projectsolutions.R;

public class PagamentoCarrinhoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvSubtotal, tvFrete, tvTotal;

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
    }

    private void findViewsById() {
        toolbar = findViewById(R.id.toolbar_endereco);
        tvSubtotal = findViewById(R.id.preco_subtotal);
        tvFrete = findViewById(R.id.preco_frete);
        tvTotal = findViewById(R.id.preco_total);
    }

}
