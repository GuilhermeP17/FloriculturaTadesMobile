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
    public ArrayList<Produto> getAllProdutos() {
        return ProdutoDAO.getProdutos();
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
