package br.com.senac.projectsolutions.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Model.Endereco;
import br.com.senac.projectsolutions.Model.Pagamento;
import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.Model.Usuario;
import br.com.senac.projectsolutions.Model.Venda;
import br.com.senac.projectsolutions.View.CarrinhoActivity;
import br.com.senac.projectsolutions.View.EnderecoCarrinhoActivity;
import br.com.senac.projectsolutions.View.LoginActivity;
import br.com.senac.projectsolutions.View.MainActivity;
import br.com.senac.projectsolutions.View.PagamentoCarrinhoActivity;
import br.com.senac.projectsolutions.View.PedidosAndamentoActivity;
import br.com.senac.projectsolutions.View.PedidosFinalizadosActivity;
import br.com.senac.projectsolutions.View.PerfilActivity;

public class DataGetter extends AsyncTask<String, Void, String> {
    private Context context;
    private String metodo;

    public DataGetter(Context context, String metodo) {
        this.context = context;
        this.metodo = metodo;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        return NetworkToolkit.doGet(url);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        boolean status = true;
        String msg = "";
        Usuario user = null;

        try {
            JSONObject json = new JSONObject(s);
            switch (metodo) {
                case "login":
                    //Caso Request seja de Login
                    if (!json.getBoolean("statusLogin")) {
                        status = false;
                        msg = json.getString("msgErro");
                    } else {
                        JSONArray dataResponse = json.getJSONArray("userInfo");
                        JSONObject usuario = (JSONObject) dataResponse.get(0);
                        user = new Usuario(
                                usuario.getInt("id"),
                                usuario.getString("nome"),
                                usuario.getString("email"),
                                usuario.getString("cpf")
                        );
                        msg = "Usuario encontrado com sucesso";
                    }
                    ((LoginActivity) context).onResponse(status, msg, user);
                    break;
                case "produto":
                    //Caso Request seja de Produto
                    ArrayList<Produto> produtos = null;
                    if (!json.getBoolean("statusRequest")) {
                        status = false;
                        msg = json.getString("msgErro");
                    } else {
                        produtos = new ArrayList<>();
                        JSONArray dataResponse = json.getJSONArray("produtosAtivos");
                        int i = 0;
                        do {
                            JSONObject auxProduto = (JSONObject) dataResponse.get(i);
                            Produto produto = new Produto(
                                    auxProduto.getInt("id"),
                                    auxProduto.getString("nome"),
                                    auxProduto.getString("descricao"),
                                    auxProduto.getString("tipo"),
                                    auxProduto.getInt("quantidade"),
                                    auxProduto.getDouble("valor")
                            );
                            produtos.add(produto);
                            i++;
                        } while (i < dataResponse.length());
                    }
                    ((MainActivity) context).onServidorResponse(status, msg, produtos);
                    break;
                case "endereco_perfil":
                    ArrayList<Endereco> enderecos = null;
                    ArrayList<Pagamento> pagamentos = null;

                    if (!json.getBoolean("statusRequest")) {
                        status = false;
                        msg = json.getString("msgErro");
                    } else {
                        enderecos = new ArrayList<>();
                        pagamentos = new ArrayList<>();
                        JSONArray dataResponse = json.getJSONArray("enderecosUser");
                        JSONArray dataResponse2 = json.getJSONArray("pagamentosUser");
                        int i = 0;
                        do {
                            JSONObject enderecoAux = (JSONObject) dataResponse.get(i);
                            JSONObject pagamentosAux = (JSONObject) dataResponse2.get(i);
                            Endereco endereco = new Endereco(
                                    enderecoAux.getInt("codigo"),
                                    enderecoAux.getString("logradouro"),
                                    enderecoAux.getInt("numero"),
                                    null,
                                    enderecoAux.getString("cep"),
                                    enderecoAux.getString("estado"),
                                    enderecoAux.getString("cidade"),
                                    enderecoAux.getString("bairro"),
                                    enderecoAux.getString("tipo")
                            );

                            Pagamento pagamento = new Pagamento();
                            pagamento.setNumeroPagamento(pagamentosAux.getString("numPagamento"));
                            pagamento.setNomeTitular(pagamentosAux.getString("nomeTitular"));
                            pagamento.setDataVencimento(pagamentosAux.getString("dtVencimento"));
                            pagamento.setTipoPagamento(pagamentosAux.getString("tipoPagamento"));


                            pagamentos.add(pagamento);
                            enderecos.add(endereco);
                            i++;
                        } while (i < dataResponse.length());
                    }

                    ((PerfilActivity) context).onServidorResponse(status, enderecos, pagamentos);
                    break;
                case "endereco_carrinho":
                    ArrayList<Endereco> enderecosCarrinho = null;

                    if (!json.getBoolean("statusRequest")) {
                        status = false;
                        msg = json.getString("msgErro");
                    } else {
                        enderecosCarrinho = new ArrayList<>();
                        JSONArray dataResponse = json.getJSONArray("enderecosUser");
                        int i = 0;
                        do {
                            JSONObject enderecoAux = (JSONObject) dataResponse.get(i);
                            Endereco endereco = new Endereco(
                                    enderecoAux.getInt("codigo"),
                                    enderecoAux.getString("logradouro"),
                                    enderecoAux.getInt("numero"),
                                    enderecoAux.getString("complemento"),
                                    enderecoAux.getString("cep"),
                                    enderecoAux.getString("estado"),
                                    enderecoAux.getString("cidade"),
                                    enderecoAux.getString("bairro"),
                                    enderecoAux.getString("tipo")
                            );
                            enderecosCarrinho.add(endereco);
                            i++;
                        } while (i < dataResponse.length());
                    }

                    ((EnderecoCarrinhoActivity) context).onServidorResponse(status, enderecosCarrinho, msg);
                    break;
                case "pagamento_carrinho":
                    ArrayList<Pagamento> pagamentosCarrinho = null;

                    if (!json.getBoolean("statusRequest")) {
                        status = false;
                        msg = json.getString("msgErro");
                    } else {
                        pagamentosCarrinho = new ArrayList<>();
                        JSONArray dataResponse = json.getJSONArray("pagamentosUser");
                        int i = 0;
                        do {
                            JSONObject pagamentosAux = (JSONObject) dataResponse.get(i);

                            Pagamento pagamento = new Pagamento();
                            pagamento.setNumeroPagamento(pagamentosAux.getString("numPagamento"));
                            pagamento.setNomeTitular(pagamentosAux.getString("nomeTitular"));
                            pagamento.setDataVencimento(pagamentosAux.getString("dtVencimento"));
                            pagamento.setTipoPagamento(pagamentosAux.getString("tipoPagamento"));


                            pagamentosCarrinho.add(pagamento);
                            i++;
                        } while (i < dataResponse.length());
                    }

                    ((PagamentoCarrinhoActivity) context).onServidorResponse(status, pagamentosCarrinho);
                    break;
                case "pedidos_andamento":
                    ArrayList<Venda> pedidosAndamento = null;

                    if (!json.getBoolean("statusRequest")) {
                        status = false;
                        msg = json.getString("msgErro");
                    } else {
                        pedidosAndamento = new ArrayList<>();
                        JSONArray dataResponse = json.getJSONArray("pedidosAndamento");
                        int i = 0;
                        do {
                            Venda venda = new Venda();
                            JSONObject vendasAux = dataResponse.getJSONObject(i);
                            JSONArray produtosVenda = vendasAux.getJSONArray("produtos");

                            venda.setCodigoVenda(vendasAux.getString("codigoVenda"));
                            venda.setValorTotal(vendasAux.getDouble("valorTotal"));
                            venda.setValorFrete(vendasAux.getDouble("valorFrete"));
                            venda.setQuantidadeItens(vendasAux.getInt("quantidadeItens"));
                            venda.setTipoPagamento(vendasAux.getString("tipoPagamento"));
                            venda.setCodigoPagamento(vendasAux.getString("codigoPagamento"));
                            venda.setData(vendasAux.getString("dataVenda"));
                            venda.setStatus(vendasAux.getString("statusPedido"));

                            int j = 0;
                            ArrayList<Produto> listProdutosVenda = new ArrayList<>();
                            do {
                                JSONObject objAux = produtosVenda.getJSONObject(j);
                                Produto prod = new Produto();
                                prod.setValor(objAux.getDouble("vlTotal"));
                                prod.setQuantidade(objAux.getInt("qtdProduto"));
                                prod.setTipo(objAux.getString("tipoProduto"));
                                prod.setNome(objAux.getString("nomeProduto"));
                                prod.setDescricao(objAux.getString("descricaoProduto"));

                                listProdutosVenda.add(prod);
                                j++;
                            } while (j < produtosVenda.length());

                            venda.setProdutos(listProdutosVenda);
                            pedidosAndamento.add(venda);
                            i++;
                        } while (i < dataResponse.length());
                    }
                    ((PedidosAndamentoActivity) context).onServidorResponse(status, pedidosAndamento, msg);
                    break;
                case "pedidos_finalizados":
                    ArrayList<Venda> pedidosFinalizado = null;

                    if (!json.getBoolean("statusRequest")) {
                        status = false;
                        msg = json.getString("msgErro");
                    } else {
                        pedidosFinalizado = new ArrayList<>();
                        JSONArray dataResponse = json.getJSONArray("pedidosFinalizados");
                        int i = 0;
                        do {
                            Venda venda = new Venda();
                            JSONObject vendasAux = dataResponse.getJSONObject(i);
                            JSONArray produtosVenda = vendasAux.getJSONArray("produtos");

                            venda.setCodigoVenda(vendasAux.getString("codigoVenda"));
                            venda.setValorTotal(vendasAux.getDouble("valorTotal"));
                            venda.setValorFrete(vendasAux.getDouble("valorFrete"));
                            venda.setQuantidadeItens(vendasAux.getInt("quantidadeItens"));
                            venda.setTipoPagamento(vendasAux.getString("tipoPagamento"));
                            venda.setCodigoPagamento(vendasAux.getString("codigoPagamento"));
                            venda.setData(vendasAux.getString("dataVenda"));
                            venda.setStatus(vendasAux.getString("statusPedido"));

                            int j = 0;
                            ArrayList<Produto> listProdutosVenda = new ArrayList<>();
                            do {
                                JSONObject objAux = produtosVenda.getJSONObject(j);
                                Produto prod = new Produto();
                                prod.setValor(objAux.getDouble("vlTotal"));
                                prod.setQuantidade(objAux.getInt("qtdProduto"));
                                prod.setTipo(objAux.getString("tipoProduto"));
                                prod.setNome(objAux.getString("nomeProduto"));
                                prod.setDescricao(objAux.getString("descricaoProduto"));

                                listProdutosVenda.add(prod);
                                j++;
                            } while (j < produtosVenda.length());

                            venda.setProdutos(listProdutosVenda);
                            pedidosFinalizado.add(venda);
                            i++;
                        } while (i < dataResponse.length());
                    }
                    ((PedidosFinalizadosActivity) context).onServidorResponse(status, pedidosFinalizado, msg);
                    break;
            }

        } catch (JSONException e) {
            String mensagemErro = "Falha ao comunicar-se com o servidor, tente novamente";
            e.printStackTrace();
            if (metodo.equalsIgnoreCase("produto")) {
                ((MainActivity) context).onServidorResponse(false, mensagemErro, null);
            } else if (metodo.equalsIgnoreCase("login")) {
                ((LoginActivity) context).onResponse(false, mensagemErro, null);
            } else if (metodo.equalsIgnoreCase("endereco")) {
                ((PerfilActivity) context).onServidorResponse(false, null, null);
            }
        }
    }
}
