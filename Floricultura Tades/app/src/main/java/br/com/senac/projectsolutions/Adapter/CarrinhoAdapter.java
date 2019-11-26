package br.com.senac.projectsolutions.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;
import br.com.senac.projectsolutions.View.CarrinhoActivity;

import static android.content.Context.MODE_PRIVATE;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoHolder> {
    private Context context;
    private ArrayList<Produto> produtosAdd;

    public CarrinhoAdapter(Context context, ArrayList<Produto> produtos) {
        this.context = context;
        produtosAdd = produtos;
    }

    @NonNull
    @Override
    public CarrinhoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarrinhoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_produto_carrinho, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CarrinhoHolder holder, final int position) {
        holder.nomeProduto.setText(produtosAdd.get(position).getNome());

        holder.quantidadeProdutos.setText(String.valueOf(produtosAdd.get(position).getQuantidade()));

        holder.precoProduto.setText("R$".concat(String.format(Locale.US, "%.2f", produtosAdd.get(position).getValor())).replace(".", ","));

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtosAdd.get(position).setValor(quantidadeManager(true, holder.quantidadeProdutos, holder.precoProduto, produtosAdd.get(position).getNome(), position));
                ((CarrinhoActivity) context).atualizaValorCompra(produtosAdd, null);
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtosAdd.get(position).setValor(quantidadeManager(false, holder.quantidadeProdutos, holder.precoProduto, produtosAdd.get(position).getNome(), position));
                ((CarrinhoActivity) context).atualizaValorCompra(produtosAdd, null);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDoCarrinho(produtosAdd.get(position).getNome());
                notifyItemRemoved(position);
                ((CarrinhoActivity) context).atualizaValorCompra(produtosAdd, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtosAdd.size();
    }

    private Double quantidadeManager(boolean adicionando, TextView quantidade, TextView preco, String nomeProduto, int position) {
        int qtdAtual = Integer.parseInt(quantidade.getText().toString());
        double precoAtual = Double.parseDouble(preco.getText().toString().replace("R$", "").replace(",", "."));
        double precoUnitatario = precoAtual / qtdAtual;
        double newPreco = 0.00;

        if (adicionando) {
            if (qtdAtual == 1) {
                newPreco = precoAtual * (qtdAtual + 1);
            } else {
                newPreco = precoUnitatario * (qtdAtual + 1);
            }

            qtdAtual += 1;
            quantidade.setText(String.valueOf(qtdAtual));
            preco.setText("R$".concat(String.format(Locale.US, "%.2f", newPreco).replace(".", ",")));
        } else {
            if (qtdAtual > 1) {
                qtdAtual -= 1;
                newPreco = precoUnitatario * (qtdAtual);
                quantidade.setText(String.valueOf(qtdAtual));
                preco.setText("R$".concat(String.format(Locale.US, "%.2f", newPreco).replace(".", ",")));
            }
        }

        try {
            editSharedSalvos(nomeProduto, qtdAtual, position, preco.getText().toString().replace("R$", "").replace(",", "."));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newPreco != 0.00 ? newPreco : precoAtual;
    }

    private void removeDoCarrinho(String nomeProduto) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ItensSalvos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(nomeProduto);
        int i = 0;
        for (Produto prod : produtosAdd) {
            if (prod.getNome().equals(nomeProduto)) {
                produtosAdd.remove(i);
                break;
            }
            i++;
        }
        editor.apply();
    }

    private void editSharedSalvos(String nomeProduto, int newQuantidade, int position, String valorProduto) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ItensSalvos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray produtosCarrinho = saveProdutosCarrinho();

        for (int i = 0; i < produtosCarrinho.length(); i++) {
            JSONObject jsonProduto = produtosCarrinho.getJSONObject(i);
            Produto produto = new Produto();
            produto.setCodigo(jsonProduto.getInt("CodigoProduto"));
            produto.setNome(jsonProduto.getString("NomeProduto"));
            produto.setDescricao(jsonProduto.getString("DescricaoProduto"));
            produto.setValor(jsonProduto.getDouble("PrecoProduto"));
            produto.setQuantidade(jsonProduto.getInt("quantidadeCarrinho"));
            produto.setTipo(jsonProduto.getString("TipoProduto"));

            if (produto.getNome().equals(nomeProduto)) {
                produto.setQuantidade(newQuantidade);
                produto.setValor(Double.parseDouble(valorProduto));

                produtosAdd.get(position).setQuantidade(produto.getQuantidade());
                produtosAdd.get(position).setValor(Double.parseDouble(valorProduto));
            }

            JSONObject json = new JSONObject();
            json.put("CodigoProduto", produto.getCodigo());
            json.put("NomeProduto", produto.getNome());
            json.put("DescricaoProduto", produto.getDescricao());
            json.put("PrecoProduto", produto.getValor());
            json.put("quantidadeCarrinho", produto.getQuantidade());
            json.put("TipoProduto", produto.getTipo());

            editor.putString(produto.getNome(), json.toString());
        }

        editor.apply();
    }

    private JSONArray saveProdutosCarrinho() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ItensSalvos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray arrayProdutos = new JSONArray();

        Map<String, ?> produtosPreferences = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : produtosPreferences.entrySet()) {
            try {
                JSONObject json = new JSONObject(entry.getValue().toString());
                arrayProdutos.put(json);
                editor.remove(entry.getKey());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.apply();

        return arrayProdutos;
    }
}
