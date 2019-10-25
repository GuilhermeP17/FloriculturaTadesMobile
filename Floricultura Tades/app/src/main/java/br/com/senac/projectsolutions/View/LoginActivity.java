package br.com.senac.projectsolutions.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import br.com.senac.projectsolutions.Controller.LoginController;
import br.com.senac.projectsolutions.Model.Usuario;
import br.com.senac.projectsolutions.R;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText fieldEmail, fieldSenha;
    private MaterialButton btnLogin, btnCadastro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewsById();

        MaterialButton btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmailValid() && isPasswordValid()){
                    String email = fieldEmail.getText().toString();
                    String senha = fieldSenha.getText().toString();
                    LoginController controller = new LoginController();
                    controller.validaLogin(LoginActivity.this, email, senha);
                }else{
                    makeToast("Campo usuário ou senha inválidos, verifique e tente novamente");
                }
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isEmailValid(){
        return !fieldEmail.getText().toString().isEmpty();
    }

    private boolean isPasswordValid(){
        return !fieldSenha.getText().toString().isEmpty();
    }

    private void makeToast(String mensagem){
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    public void onResponse(boolean status, String msgStatus, Usuario usuario){
        if(status){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }else{
           makeToast(msgStatus);
        }
    }

    private void findViewsById(){
        fieldEmail = findViewById(R.id.edit_email);
        fieldSenha = findViewById(R.id.edit_senha);
        btnLogin = findViewById(R.id.btn_login);
        btnCadastro = findViewById(R.id.btn_cadastro);
    }
}
