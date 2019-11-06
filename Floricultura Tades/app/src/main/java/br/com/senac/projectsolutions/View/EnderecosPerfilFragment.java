package br.com.senac.projectsolutions.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.CarrinhoAdapter;
import br.com.senac.projectsolutions.Adapter.EnderecoAdapter;
import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.R;

public class EnderecosPerfilFragment extends Fragment {
    private ArrayList<Endereco> enderecos;
    private Context context;
    private RecyclerView recyclerView;

    public EnderecosPerfilFragment(ArrayList<Endereco> enderecos, Context context) {
        this.enderecos = enderecos;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enderecos_perfil, container, false);
        findViewsById(view);

        setRecyclerView();
        return view;
    }

    private void findViewsById(View view) {
        recyclerView = view.findViewById(R.id.recycler_endereco);
    }

    private void setRecyclerView(){
        LinearLayoutManager gridManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(gridManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, gridManager.getOrientation()));
        EnderecoAdapter adapter = new EnderecoAdapter(enderecos);
        recyclerView.setAdapter(adapter);
    }
}