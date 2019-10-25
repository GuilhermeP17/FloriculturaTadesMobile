/*
 * Created by: Guilherme Pereira
 * Date: Out/2019
 */
package WebService;

import DAO.ProdutoDAO;
import Model.Produto;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * REST Web Service
 *
 * @author guilherme.pereira
 */
@Path("/produtos")
public class ProdutosWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FloriculturaWS
     */
    public ProdutosWS() {
    }

    /**
     * Retorna uma Intancia de WebService.ProdutosWS
     *
     * @return um Arraylist com todos os produtos no banco de dados
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allprodutos")
    public String getAllProdutos() {
        ArrayList<Produto> produtos = ProdutoDAO.getProdutos();

        JSONObject response = new JSONObject();
        if (produtos.size() > 0) {
            response.put("statusRequest", true);
            
            JSONArray produtosAtivos = new JSONArray();
            for (Produto produto : produtos) {
                JSONObject prod = new JSONObject();
                prod.put("id", produto.getCodigo());
                prod.put("nome", produto.getNome());
                prod.put("descricao", produto.getDescricao());
                prod.put("tipo", produto.getTipo());
                prod.put("quantidade", produto.getQuantidadeEstoque());
                prod.put("valor", produto.getValorUnitario());
                
                produtosAtivos.add(prod);
            }
            response.put("produtosAtivos", produtosAtivos);
        } else {
            response.put("statusRequest", false);
            response.put("msgErro", "Não foram encontrados registros no servidor");
        }

        return response.toString();
    }

    /**
     * Retorna uma Intancia de WebService.ProdutosWS
     *
     * @param codigoProduto
     * @return um objeto do produto especifico cadastrado no banco de dados
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/produto/{codigoProduto}")
    public Produto getProduto(@PathParam("codigoProduto") int codigoProduto) {
        return ProdutoDAO.getProduto(codigoProduto);
    }

    /**
     * Metodo PUT para atualizar ou criar uma intancia de ProdutosWS
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
