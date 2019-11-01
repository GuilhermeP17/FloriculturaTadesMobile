package br.com.senac.projectsolutions.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
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
    private ProgressBar progressLogin;

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

                    progressLogin.setIndeterminate(true);
                    progressLogin.setVisibility(View.VISIBLE);

                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

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
        progressLogin.setIndeterminate(false);
        progressLogin.setVisibility(View.GONE);
        if(status){
            SharedPreferences preferences = getSharedPreferences("SessaoUsuario", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", usuario.getEmail());
            editor.putString("cpf", usuario.getCpf());
            editor.putString("nome", usuario.getNome());
            editor.putInt("codigo", usuario.getCodigo());
            editor.apply();

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
        progressLogin = findViewById(R.id.progress_login);
    }
}
