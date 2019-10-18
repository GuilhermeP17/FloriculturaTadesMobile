package DAO;

import Model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alexsander.mrocha
 */
public class ProdutoDAO {

    private static final Database db = new Database();

    public static ArrayList<Produto> getProdutos() {
        ArrayList<Produto> produtos = new ArrayList<>();
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("SELECT"
                    + " p.id_produto,"
                    + " p.nome,"
                    + " p.descricao,"
                    + " p.tipo,"
                    + " p.qtd_estoque,"
                    + " p.valor_unidade,"
                    + " nome_arquivo "
                    + " FROM tbl_produtos AS p"
                    + " inner join tbl_imagem on"
                    + " p.id_produto = tbl_imagem.fk_produto"
                    + " WHERE p.status = 0;");
            
            ResultSet rs = query.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Produto produto = new Produto(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5),
                            rs.getDouble(6),
                            rs.getString(7));
                    produtos.add(produto);
                }
            }

            conn.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

        return produtos;
    }

    public static Produto getProduto(int codigo) {
        Produto produto = null;
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("SELECT"
                    + " p.id_produto,"
                    + " p.nome,"
                    + " p.descricao,"
                    + " p.tipo,"
                    + " p.qtd_estoque,"
                    + " p.valor_unidade,"
                    + " nome_arquivo"
                    + " FROM tbl_produtos AS p"
                    + " INNER JOIN tbl_imagem ON p.id_produto = tbl_imagem.fk_produto"
                    + " WHERE tbl_imagem.fk_produto = ? AND p.status = 0;");

            query.setInt(1, codigo);
            ResultSet rs = query.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Produto prod = new Produto(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5),
                            rs.getDouble(6),
                            rs.getString(7)
                    );
                    produto = prod;
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return produto;
    }
}
