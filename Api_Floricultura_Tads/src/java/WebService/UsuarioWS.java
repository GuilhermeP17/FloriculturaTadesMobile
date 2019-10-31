/*
 * Created by: Guilherme Pereira
 * Date: Out/2019
 */
package WebService;

import DAO.UsuarioDAO;
import Model.Endereco;
import Model.Usuario;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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
    @Path("/allusuarios")
    public ArrayList<Usuario> getAllUsuarios() {
        ArrayList<Usuario> usuarios = UsuarioDAO.getUsuarios();
        return usuarios;
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
    public void getJsonUsuario(String content) {

    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cadastrar")
    public String cadastrarUsuario(Usuario usuario) {
        boolean status = UsuarioDAO.salvarUsuario(usuario);
        
        JSONObject jsonResponse = new JSONObject();
        if (status) {
            jsonResponse.put("status", true);
            jsonResponse.put("msgStatus", "Usuário salvo com sucesso");
        }else{
            jsonResponse.put("status", false);
            jsonResponse.put("msgStatus", "Não foi possivel cadastrar o usuário");
        }
        
        return jsonResponse.toString();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/alterar")
    public String alterarUsuario(String content) {
        return "alterar";
    }
}
