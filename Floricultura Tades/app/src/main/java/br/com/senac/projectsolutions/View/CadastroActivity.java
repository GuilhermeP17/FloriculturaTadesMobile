package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import br.com.senac.projectsolutions.Controller.CadastroController;
import br.com.senac.projectsolutions.R;

public class CadastroActivity extends AppCompatActivity {
    private MaterialButton btnCadastro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnCadastro = findViewById(R.id.btn_cadastro);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroController controller = new CadastroController();
                controller.validaLogin(CadastroActivity.this);
            }
        });
    }
}
