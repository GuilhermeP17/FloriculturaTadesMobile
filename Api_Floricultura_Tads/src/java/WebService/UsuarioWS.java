/*
 * Created by: Guilherme Pereira
 * Date: Out/2019
 */
package WebService;

import DAO.UsuarioDAO;
import Model.Endereco;
import Model.Pagamento;
import Model.Usuario;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
 * @author guilherme.psilva103
 */
@Path("/usuarios")
public class UsuarioWS {

    @Context
    private UriInfo context;

    public UsuarioWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/perfil/{codigoUser}")
    public String getInfoPerfil(@PathParam("codigoUser") int codigoUser){
        ArrayList<Endereco> enderecos = UsuarioDAO.getEnderecos(codigoUser);
        ArrayList<Pagamento> pagamentos = UsuarioDAO.getPagamentosCadastrados(codigoUser);
        
        JSONObject response = new JSONObject();
        if (enderecos.size() > 0) {
            response.put("statusRequest", true);
            
            JSONArray enderecosAtivos = new JSONArray();
            JSONArray pagamentosAtivos = new JSONArray();
            
            for (Endereco endereco : enderecos) {
                JSONObject end = new JSONObject();
                end.put("codigo", endereco.getCodigo());
                end.put("logradouro", endereco.getLogradouro());
                end.put("complemento", endereco.getComplemento());
                end.put("numero", endereco.getNumero());
                end.put("bairro", endereco.getBairro());
                end.put("cidade", endereco.getCidade());
                end.put("estado", endereco.getEstado());
                end.put("cep", endereco.getCep());
                end.put("tipo", endereco.getTipoEndereco());
                
                enderecosAtivos.add(end);
            }
            
            for(Pagamento pagamento : pagamentos){
                JSONObject pag = new JSONObject();
                pag.put("idPagamento", pagamento.getId());
                pag.put("numPagamento", pagamento.getNumeroPagamento());
                pag.put("nomeTitular", pagamento.getNomeTitular());
                pag.put("dtVencimento", pagamento.getDataVencimento());
                pag.put("tipoPagamento", pagamento.getTipoPagamento());
                
                pagamentosAtivos.add(pag);
            }
            
            response.put("pagamentosUser", pagamentosAtivos);
            response.put("enderecosUser", enderecosAtivos);
        }else{
            response.put("statusRequest", false);
            response.put("msgErro", "Não foram encontrados endereços cadastrados");
        }
        return response.toString();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validausuario/{emailUser}/{senhaUser}")
    public String validaLogin(@PathParam("emailUser") String email,
            @PathParam("senhaUser") String senha) {
        boolean loginCorreto = UsuarioDAO.verifyLogin(email, senha);

        JSONObject response = new JSONObject();
        if (loginCorreto) {
            Usuario user = UsuarioDAO.getInfoUser(email);
            response.put("statusLogin", loginCorreto);

            JSONObject userInfo = new JSONObject();
            userInfo.put("id", user.getCodigo());
            userInfo.put("nome", user.getNome());
            userInfo.put("email", user.getEmail());
            userInfo.put("setor", user.getNomeSetor());
            userInfo.put("cpf", user.getCPF());

            JSONArray arrayUser = new JSONArray();
            arrayUser.add(userInfo);

            response.put("userInfo", arrayUser);
        } else {
            response.put("statusLogin", loginCorreto);
            response.put("msgErro", "Usuário ou Senha inválidos");
        }

        return response.toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/infousuario/{codigoUsuario}")
    public Usuario getUsuario(@PathParam("codigoUsuario") int codigoUsuario) {
        return UsuarioDAO.getUsuario(codigoUsuario);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void getJsonUsuario(String content) {}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cadastrar/usuario")
    public String cadastrarUsuario(Usuario usuario) {
        boolean status = UsuarioDAO.salvarUsuario(usuario);

        JSONObject jsonResponse = new JSONObject();
        if (status) {
            jsonResponse.put("status", true);
            jsonResponse.put("msgStatus", "Usuário salvo com sucesso");

            Usuario user = UsuarioDAO.getInfoUser(usuario.getEmail());
            JSONObject userInfo = new JSONObject();
            userInfo.put("id", user.getCodigo());
            userInfo.put("nome", user.getNome());
            userInfo.put("email", user.getEmail());
            userInfo.put("setor", user.getNomeSetor());
            userInfo.put("cpf", user.getCPF());

            JSONArray arrayUser = new JSONArray();
            arrayUser.add(userInfo);

            jsonResponse.put("userInfo", arrayUser);
        } else {
            jsonResponse.put("status", false);
            jsonResponse.put("msgStatus", "Não foi possivel cadastrar o usuário");
        }

        return jsonResponse.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/alterar/usuario")
    public String alterarUsuario(String content) {
        return "alterar";
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cadastrar/endereco")
    public String cadastrarEndereco() {
       return "Cadastro Endereco"; 
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cadastrar/pagamento")
    public String cadastrarPagamento() {
       return "Cadastro Pagamento";
    }
}
