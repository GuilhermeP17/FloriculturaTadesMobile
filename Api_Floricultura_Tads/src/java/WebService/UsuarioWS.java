/*
 * Created by: Guilherme Pereira
 * Date: Out/2019
 */
package WebService;

import DAO.UsuarioDAO;
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

            JSONArray arrayUser = new JSONArray();
            arrayUser.add(user.getCodigo());
            arrayUser.add(user.getNome());
            arrayUser.add(user.getEmail());
            arrayUser.add(user.getSenha());
            arrayUser.add(user.getNomeSetor());

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
    public String cadastrarUsuario(String content) {
        return "cadastro";
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/alterar")
    public String alterarUsuario(String content) {
        return "alterar";
    }
}
