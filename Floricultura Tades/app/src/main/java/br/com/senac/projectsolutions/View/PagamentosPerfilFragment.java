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

import br.com.senac.projectsolutions.Adapter.EnderecoAdapter;
import br.com.senac.projectsolutions.Adapter.PagamentosAdapter;
import br.com.senac.projectsolutions.Model.Pagamento;
import br.com.senac.projectsolutions.R;

public class PagamentosPerfilFragment extends Fragment {
    private Context context;
    private ArrayList<Pagamento> pagamentos;
    private RecyclerView recyclerView;

    public PagamentosPerfilFragment(ArrayList<Pagamento> pagamentos, Context context){
        this.context = context;
        this.pagamentos = pagamentos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_pagamento_perfil , container, false);
        findViewsById(view);

        setRecyclerView();
        return view;
    }

    private void findViewsById(View view) {
        recyclerView = view.findViewById(R.id.recycler_pagamento);
    }

    private void setRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, linearLayoutManager.getOrientation()));
        PagamentosAdapter adapter = new PagamentosAdapter(pagamentos);
        recyclerView.setAdapter(adapter);
    }
}
