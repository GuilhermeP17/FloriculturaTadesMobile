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
    public String getInfoPerfil(@PathParam("codigoUser") int codigoUser) {
        ArrayList<Endereco> enderecos = UsuarioDAO.getEnderecos(codigoUser);
        ArrayList<Pagamento> pagamentos = UsuarioDAO.getPagamentosCadastrados(codigoUser);

        JSONObject response = new JSONObject();
        if (enderecos.size() > 0) {
            response.put("statusRequest", true);

            JSONArray enderecosAtivos = new JSONArray();
            JSONArray pagamentosAtivos = new JSONArray();

            JSONArray infoUser = arrayInfoUser(codigoUser);
            response.put("infoUser", infoUser);

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

            for (Pagamento pagamento : pagamentos) {
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
        } else {
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

    public Usuario getUsuario(int codigoUsuario) {
        return UsuarioDAO.getUsuario(codigoUsuario);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void getJsonUsuario(String content) {
    }

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
    public String alterarUsuario(Usuario usuario) {
        boolean updateSenha = false;
        if (!usuario.getSenha().isEmpty()) {
            updateSenha = true;
        }

        boolean result = UsuarioDAO.alterarUsuario(usuario, updateSenha);

        JSONObject jsonResponse = new JSONObject();
        if (result) {
            JSONArray pagamentosAtivos = pagamentosUser(usuario.getCodigo());
            JSONArray enderecosAtivos = enderecosUser(usuario.getCodigo());
            JSONArray infoUser = arrayInfoUser(usuario.getCodigo());

            jsonResponse.put("status", true);
            jsonResponse.put("msgStatus", "Alteração feita com sucesso");

            jsonResponse.put("infoUser", infoUser);
            jsonResponse.put("pagamentosUser", pagamentosAtivos);
            jsonResponse.put("enderecosUser", enderecosAtivos);
        } else {
            jsonResponse.put("status", false);
            jsonResponse.put("msgStatus", "Não foi possivel alterar o cadastro");
        }

        return jsonResponse.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/alterar/endereco")
    public String alterarEndereco(Endereco endereco) {
        return "alterar Endereci";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cadastrar/endereco")
    public String cadastrarEndereco(Endereco endereco) {
        Usuario user = new Usuario();
        user.setLogradouro(endereco.getLogradouro());
        user.setComplemento(endereco.getComplemento());
        user.setNumero(endereco.getNumero());
        user.setBairro(endereco.getBairro());
        user.setCidade(endereco.getCidade());
        user.setEstado(endereco.getEstado());
        user.setCep(endereco.getCep());
        user.setTipoEndereco(endereco.getTipoEndereco());
        user.setCodigo(endereco.getCodigoUsuario());

        boolean result = UsuarioDAO.salvarEndereco(user, endereco.getCodigoUsuario());

        JSONObject jsonResponse = new JSONObject();
        if (result) {

            JSONArray pagamentosAtivos = pagamentosUser(user.getCodigo());
            JSONArray enderecosAtivos = enderecosUser(user.getCodigo());
            JSONArray infoUser = arrayInfoUser(user.getCodigo());

            jsonResponse.put("infoUser", infoUser);
            jsonResponse.put("pagamentosUser", pagamentosAtivos);
            jsonResponse.put("enderecosUser", enderecosAtivos);
        } else {
            jsonResponse.put("status", false);
            jsonResponse.put("msgStatus", "Não foi possivel cadastrar o endereco");
        }

        return "sadas";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/alterar/pagamento")
    public String alterarPagamento(Pagamento pagamento) {
        return "Alterar Pagamento";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cadastrar/pagamento")
    public String cadastrarPagamento(Pagamento pagamento) {
        boolean result = UsuarioDAO.salvarPagamento(pagamento);

        JSONObject jsonResponse = new JSONObject();
        if (result) {
            JSONArray pagamentosAtivos = pagamentosUser(pagamento.getIdUsuario());
            JSONArray enderecosAtivos = enderecosUser(pagamento.getIdUsuario());
            JSONArray infoUser = arrayInfoUser(pagamento.getIdUsuario());

            jsonResponse.put("status", true);
            jsonResponse.put("msgStatus", "Pagamento cadastrado com sucesso");

            jsonResponse.put("infoUser", infoUser);
            jsonResponse.put("pagamentosUser", pagamentosAtivos);
            jsonResponse.put("enderecosUser", enderecosAtivos);
        } else {
            jsonResponse.put("status", false);
            jsonResponse.put("msgStatus", "Não foi possivel cadastrar o método de pagamento");
        }

        return jsonResponse.toString();
    }

    private JSONArray pagamentosUser(int codigUser) {
        ArrayList<Pagamento> pagamentos = UsuarioDAO.getPagamentosCadastrados(codigUser);

        JSONArray pagamentosAtivos = new JSONArray();

        for (Pagamento pagamento : pagamentos) {
            JSONObject pag = new JSONObject();
            pag.put("idPagamento", pagamento.getId());
            pag.put("numPagamento", pagamento.getNumeroPagamento());
            pag.put("nomeTitular", pagamento.getNomeTitular());
            pag.put("dtVencimento", pagamento.getDataVencimento());
            pag.put("tipoPagamento", pagamento.getTipoPagamento());

            pagamentosAtivos.add(pag);
        }

        return pagamentosAtivos;
    }

    private JSONArray enderecosUser(int codigoUser) {
        ArrayList<Endereco> enderecos = UsuarioDAO.getEnderecos(codigoUser);

        JSONArray enderecosAtivos = new JSONArray();

        for (Endereco enderecoAux : enderecos) {
            JSONObject end = new JSONObject();
            end.put("codigo", enderecoAux.getCodigo());
            end.put("logradouro", enderecoAux.getLogradouro());
            end.put("complemento", enderecoAux.getComplemento());
            end.put("numero", enderecoAux.getNumero());
            end.put("bairro", enderecoAux.getBairro());
            end.put("cidade", enderecoAux.getCidade());
            end.put("estado", enderecoAux.getEstado());
            end.put("cep", enderecoAux.getCep());
            end.put("tipo", enderecoAux.getTipoEndereco());

            enderecosAtivos.add(end);
        }

        return enderecosAtivos;
    }

    private JSONArray arrayInfoUser(int codigoUser) {
        JSONArray infoUser = new JSONArray();
        JSONObject user = new JSONObject();

        Usuario userSelect = UsuarioDAO.getUsuario(codigoUser);
        user.put("codigo", userSelect.getCodigo());
        user.put("nome", userSelect.getNome());
        user.put("email", userSelect.getEmail());
        user.put("cpf", userSelect.getCPF());

        infoUser.add(user);

        return infoUser;
    }
}
