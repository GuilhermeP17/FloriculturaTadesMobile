/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import DAO.VendaDAO;
import Model.Produto;
import Model.Usuario;
import Model.Venda;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * REST Web Service
 *
 * @author guilherme.pereira
 */
@Path("/venda")
public class VendaWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VendaWS
     */
    public VendaWS() {
    }

    /**
     * Retrieves representation of an instance of WebService.VendaWS
     *
     * @param codigoUser
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pedidos/finalizados/{codigoUsuario}")
    public String getPedidosFinalizadas(@PathParam("codigoUsuario") int codigoUser) {
        ArrayList<Venda> pedidos = VendaDAO.getPedidosUsuario(codigoUser, true);

        JSONObject response = new JSONObject();
        if (pedidos.size() > 0) {
            response.put("statusRequest", true);
            response.put("isEmpty", false);

            JSONArray pedidosFinalizados = new JSONArray();
            for (Venda venda : pedidos) {
                JSONObject pedido = new JSONObject();
                pedido.put("codigoVenda", venda.getCodigoVenda());
                pedido.put("quantidadeItens", venda.getQuantidadeItens());
                pedido.put("dataVenda", venda.getData());
                pedido.put("statusPedido", venda.getStatus());

                JSONArray produtosVenda = new JSONArray();
                for (Produto prod : venda.getProdutos()) {
                    JSONObject prodAux = new JSONObject();
                    prodAux.put("nomeProduto", prod.getNome());
                    prodAux.put("descricaoProduto", prod.getDescricao());
                    prodAux.put("tipoProduto", prod.getTipo());
                    prodAux.put("vlTotal", prod.getValorUnitario());
                    prodAux.put("qtdProduto", prod.getQuantidadeEstoque());

                    produtosVenda.add(prodAux);
                }
                pedido.put("produtos", produtosVenda);

                pedidosFinalizados.add(pedido);
            }
            response.put("pedidosFinalizados", pedidosFinalizados);
        } else if (pedidos.isEmpty()) {
            response.put("statusRequest", true);
            response.put("isEmpty", true);
            response.put("msgErro", "Não foram encontrados pedidos finalizados");
        } else {
            response.put("statusRequest", false);
            response.put("msgErro", "Não foi possivel comunicar-se com o servidor");
        }

        return response.toString();
    }

    @GET
    @Path("/pedidos/andamento/{codigoUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPedidosAndamento(@PathParam("codigoUsuario") int codigoUser) {
        ArrayList<Venda> pedidos = VendaDAO.getPedidosUsuario(codigoUser, false);

        JSONObject response = new JSONObject();
        if (pedidos.size() > 0) {
            response.put("statusRequest", true);
            response.put("isEmpty", false);

            JSONArray pedidosFinalizados = new JSONArray();
            for (Venda venda : pedidos) {
                JSONObject pedido = new JSONObject();
                pedido.put("codigoVenda", venda.getCodigoVenda());
                pedido.put("quantidadeItens", venda.getQuantidadeItens());
                pedido.put("dataVenda", venda.getData());
                pedido.put("statusPedido", venda.getStatus());
                pedido.put("valorTotal", venda.getValorTotal());
                pedido.put("tipoPagamento", venda.getTipoPagamento());
                pedido.put("codigoPagamento", venda.getCodigoPagamento());
                pedido.put("valorFrete", venda.getValorFrete());

                JSONArray produtosVenda = new JSONArray();
                for (Produto prod : venda.getProdutos()) {
                    JSONObject prodAux = new JSONObject();
                    prodAux.put("nomeProduto", prod.getNome());
                    prodAux.put("descricaoProduto", prod.getDescricao());
                    prodAux.put("tipoProduto", prod.getTipo());
                    prodAux.put("vlTotal", prod.getValorUnitario());
                    prodAux.put("qtdProduto", prod.getQuantidadeEstoque());

                    produtosVenda.add(prodAux);
                }
                pedido.put("produtos", produtosVenda);

                pedidosFinalizados.add(pedido);
            }
            response.put("pedidosFinalizados", pedidosFinalizados);
        } else if (pedidos.isEmpty()) {
            response.put("statusRequest", true);
            response.put("isEmpty", true);
            response.put("msgErro", "Não foram encontrados pedidos em Andamento");
        } else {
            response.put("statusRequest", false);
            response.put("msgErro", "Não foi possivel comunicar-se com o servidor");
        }

        return response.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void getJsonUsuario(String content) {

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cadastrar")
    public String realizaVenda(Venda venda) {
        boolean status = VendaDAO.salvarVenda(venda);

        JSONObject jsonResponse = new JSONObject();
        if (status) {
            jsonResponse.put("status", true);
            jsonResponse.put("msgStatus", "Venda salva com sucesso!");
        } else {
            jsonResponse.put("status", false);
            jsonResponse.put("msgStatus", "Não foi possivel cadastrar a venda");
        }

        return jsonResponse.toString();
    }
}
