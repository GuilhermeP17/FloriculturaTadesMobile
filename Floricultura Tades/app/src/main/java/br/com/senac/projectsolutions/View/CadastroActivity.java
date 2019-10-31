package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.senac.projectsolutions.Controller.CadastroController;
import br.com.senac.projectsolutions.R;

public class CadastroActivity extends AppCompatActivity {
    private MaterialButton btnCadastro;
    private TextInputEditText nome, sobrenome, email, cpf, senha, confirmaSenha;
    private TextInputEditText endereco, numero, cep, complemento, cidade, bairro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        findViewsById();

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkEmptyFields()){
                    CadastroController controller = new CadastroController();
                    controller.validaLogin(CadastroActivity.this,
                            //// TODO: 31/10/2019 Incluir campo de Estado
                            new String[]{
                                    nome.getText().toString(),
                                    sobrenome.getText().toString(),
                                    email.getText().toString(),
                                    cpf.getText().toString(),
                                    senha.getText().toString(),
                                    endereco.getText().toString(),
                                    numero.getText().toString(),
                                    cep.getText().toString(),
                                    complemento.getText().toString(),
                                    cidade.getText().toString(),
                                    bairro.getText().toString()});
                }
            }
        });
    }

    private boolean checkEmptyFields(){
        boolean result = false;
        String mensagemCampoNulo = "Campo inválido";

        if (nome.getText().toString().isEmpty()){
            TextInputLayout nome = findViewById(R.id.layout_nome);
            nome.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout nome = findViewById(R.id.layout_nome);
            nome.setError(null);
        }

        if (sobrenome.getText().toString().isEmpty()){
            TextInputLayout sobrenome = findViewById(R.id.layout_sobrenome);
            sobrenome.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout sobrenome = findViewById(R.id.layout_sobrenome);
            sobrenome.setError(null);
        }

        if (email.getText().toString().isEmpty()){
            TextInputLayout email = findViewById(R.id.layout_email);
            email.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout email = findViewById(R.id.layout_email);
            email.setError(null);
        }

        if (cpf.getText().toString().isEmpty()){
            TextInputLayout cpf = findViewById(R.id.layout_cpf);
            cpf.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout cpf = findViewById(R.id.layout_cpf);
            cpf.setError(null);
        }

        if (senha.getText().toString().isEmpty()){
            TextInputLayout senha = findViewById(R.id.layout_senha);
            senha.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout senha = findViewById(R.id.layout_senha);
            senha.setError(null);
        }

        if (confirmaSenha.getText().toString().isEmpty()){
            TextInputLayout confirmaSenha = findViewById(R.id.layout_confirma_senha);
            confirmaSenha.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout confirmaSenha = findViewById(R.id.layout_confirma_senha);
            if (!this.confirmaSenha.getText().toString().equals(senha.getText().toString())){
                confirmaSenha.setError("Senha não confere com a senha anterior");
            }else{
                confirmaSenha.setError(null);
            }
        }

        if (endereco.getText().toString().isEmpty()){
            TextInputLayout endereco = findViewById(R.id.layout_endereco);
            endereco.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout endereco = findViewById(R.id.layout_endereco);
            endereco.setError(null);
        }

        if (numero.getText().toString().isEmpty()){
            TextInputLayout numero = findViewById(R.id.layout_numero);
            numero.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout numero = findViewById(R.id.layout_numero);
            numero.setError(null);
        }

        if (cep.getText().toString().isEmpty()){
            TextInputLayout cep = findViewById(R.id.layout_cep);
            cep.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout cep = findViewById(R.id.layout_cep);
            cep.setError(null);
        }

        if (complemento.getText().toString().isEmpty()){
            TextInputLayout complemento = findViewById(R.id.layout_complemento);
            complemento.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout complemento = findViewById(R.id.layout_complemento);
            complemento.setError(null);
        }

        if (cidade.getText().toString().isEmpty()){
            TextInputLayout cidade = findViewById(R.id.layout_cidade);
            cidade.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout cidade = findViewById(R.id.layout_cidade);
            cidade.setError(null);
        }

        if (bairro.getText().toString().isEmpty()){
            TextInputLayout bairro = findViewById(R.id.layout_bairro);
            bairro.setError(mensagemCampoNulo);
            result = true;
        }else{
            TextInputLayout bairro = findViewById(R.id.layout_bairro);
            bairro.setError(null);
        }

        return result;
    }

    private void findViewsById(){
        nome = findViewById(R.id.et_nome);
        sobrenome = findViewById(R.id.et_sobrenome);
        email = findViewById(R.id.et_email);
        cpf = findViewById(R.id.et_cpf);
        senha = findViewById(R.id.et_senha);
        confirmaSenha = findViewById(R.id.et_confirma_senha);
        endereco = findViewById(R.id.et_endereco);
        numero = findViewById(R.id.et_numero);
        cep = findViewById(R.id.et_cep);
        complemento = findViewById(R.id.et_complemento);
        cidade = findViewById(R.id.et_cidade);
        bairro = findViewById(R.id.et_bairro);
        btnCadastro = findViewById(R.id.btn_cadastro);
    }
}
