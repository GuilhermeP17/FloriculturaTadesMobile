package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.EnderecoAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.R;

public class EnderecoCarrinhoActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        findViewsById();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        PerfilController controller = new PerfilController();
        controller.getInfoEndereco(EnderecoCarrinhoActivity.this, "endereco_carrinho");
    }

    public void onServidorResponse(boolean status, ArrayList<Endereco> enderecos, String msg){
        if (status){
            setRecyclerView(enderecos);
        }else{
            View parentView = findViewById(android.R.id.content);
            Snackbar.make(parentView, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    private void findViewsById(){
        toolbar = findViewById(R.id.toolbar_endereco);
    }

    private void setRecyclerView(ArrayList<Endereco> enderecos) {
        LinearLayoutManager linearManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.recycler_endereco);
        recyclerView.setLayoutManager(linearManager);
        EnderecoAdapter adapter = new EnderecoAdapter(enderecos);
        recyclerView.setAdapter(adapter);
    }
}
