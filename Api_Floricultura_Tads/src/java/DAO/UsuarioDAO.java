package DAO;

import Model.Usuario;
import Utils.Criptografia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author alexsander.mrocha
 */
public class UsuarioDAO {

    private static final Database db = new Database();

    public static boolean salvarUsuario(Usuario u) {
        Connection conn = db.obterConexao();
        try {
            PreparedStatement queryUsuario = conn.prepareStatement("INSERT INTO "
                    + " tbl_usuario(nome, email, senha, cpf, fk_setor, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            queryUsuario.setString(1, u.getNome());
            queryUsuario.setString(2, u.getEmail());
            queryUsuario.setString(3, Criptografia.criptografar(u.getSenha()));
            queryUsuario.setString(4, u.getCPF());
            queryUsuario.setInt(5, 4);
            queryUsuario.setInt(6, 0);

            queryUsuario.executeUpdate();

            ResultSet rs = queryUsuario.getGeneratedKeys();
            int idUsusarioInsert = 0;
            if (rs.next()) {
                idUsusarioInsert = rs.getInt(1);
            }
            if (!salvarEndereco(u, idUsusarioInsert)) {
                deleteUserOnErrorEndereco(idUsusarioInsert);
                return false;
            }

            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

        return true;
    }

    public static Boolean salvarEndereco(Usuario user, int idInsertUsuario) {
        Connection conn = db.obterConexao();

        try {
            PreparedStatement queryEndereco = conn.prepareStatement("INSERT INTO"
                    + " tbl_endereco(logradouro, numero, bairro, cidade, estado, cep, tipo, fk_usuario)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

            queryEndereco.setString(1, user.getLogradouro());
            queryEndereco.setInt(2, user.getNumero());
            queryEndereco.setString(3, user.getBairro());
            queryEndereco.setString(4, user.getCidade());
            queryEndereco.setString(5, user.getEstado());
            queryEndereco.setString(6, user.getCep());
            queryEndereco.setString(7, user.getTipoEndereco());
            queryEndereco.setInt(8, idInsertUsuario);

            queryEndereco.executeUpdate();

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    private static Boolean deleteUserOnErrorEndereco(int codigoUsuario) {
        Connection conn = db.obterConexao();

        try {
            PreparedStatement query = conn.prepareStatement("DELETE FROM tbl_usuario WHERE id_usuario = ?");

            query.setInt(1, codigoUsuario);
            
            if (!query.execute()) {
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean alterarUsuario(Usuario u) {
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("UPDATE"
                    + " tbl_usuario SET nome = ?, email = ?, senha = ?, fk_setor = ? WHERE id_usuario = ?;");

            query.setString(1, u.getNome());
            query.setString(2, u.getEmail());
            query.setString(3, u.getSenha());
            query.setInt(4, u.getSetor());
            query.setInt(5, u.getCodigo());

            query.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public static boolean excluirUsuario(int uCodigo) {
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("UPDATE tbl_usuario SET status = 1 WHERE id_usuario = ?");

            query.setInt(1, uCodigo);

            query.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public static ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("SELECT u.id_usuario, u.nome, u.email, u.senha, u.fk_setor, s.nome_setor"
                    + " FROM tbl_usuario AS u"
                    + " INNER JOIN tbl_setor AS s ON u.fk_setor = s.id_setor"
                    + " WHERE u.status = 0;");

            ResultSet rs = query.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Usuario user = new Usuario(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5)
                    );
                    user.setNomeSetor(rs.getString(6));
                    usuarios.add(user);
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return usuarios;
    }

    public static Usuario getUsuario(int codigoUsuario) {
        Usuario usuarios = null;
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("SELECT u.id_usuario, u.nome, u.email, u.senha, u.fk_setor, s.nome_setor"
                    + " FROM tbl_usuario AS u"
                    + " INNER JOIN tbl_setor AS s ON u.fk_setor = s.id_setor"
                    + " WHERE u.id_usuario  = ? AND u.status = 0 ;");

            query.setInt(1, codigoUsuario);
            ResultSet rs = query.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Usuario user = new Usuario(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5));
                    user.setNomeSetor(rs.getString(6));
                    usuarios = user;
                }
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return usuarios;
    }

    public static boolean verifyLogin(String email, String senha) {
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM tbl_usuario WHERE email= ? AND senha = ? AND status = 0;");
            query.setString(1, email);
            query.setString(2, senha);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    public static Usuario getInfoUser(String uEmail) {
        Usuario user = null;
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("SELECT id_usuario, nome, email, nome_setor, cpf from tbl_usuario as u"
                    + " INNER JOIN tbl_setor AS s ON u.fk_setor = s.id_setor"
                    + " WHERE email = ?;");

            query.setString(1, uEmail);

            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                user = new Usuario(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return user;
    }
}
