package DAO;

import Model.Produto;
import Model.Usuario;
import Model.Venda;
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
public class VendaDAO {

    private static final Database db = new Database();

    public static boolean realizarVenda(Venda v) {
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("INSERT INTO"
                    + " tbl_venda(id_produto, id_usuario, qtd_itens, cpf_cliente, status, data_venda)"
                    + "VALUES (?, ?, ?, ?, ?, NOW());");

            int rs;
//            for (int i = 0; i < v.getProdutoArray().length; i++) {
//                query.setInt(1, v.getProdutoArrayPosition(i));
//                query.setInt(2, v.getIdFuncionario());
//                query.setInt(3, v.getProdutoQtdArrayPosition(i));
//                query.setString(4, v.getCpfCliente());
//                query.setInt(5, 0);
//                rs = query.executeUpdate();
//                if (rs != 0) {
//                    atualizaEstoque(v.getProdutoQtdArrayPosition(i), v.getProdutoArrayPosition(i), "-");
//                }
//            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e);
            return false;
        }

        return true;
    }

    public static boolean excluirVenda(int vCodigo) {
//        Venda v = new Venda();
//        Connection conn = db.obterConexao();
//        try {
//            PreparedStatement select = conn.prepareStatement("SELECT id_produto, qtd_itens FROM tbl_venda WHERE id_venda = ?");
//            PreparedStatement query = conn.prepareStatement("UPDATE tbl_venda SET status = 1 WHERE id_venda = ?");
//
//            query.setInt(1, vCodigo);
//            int rs = query.executeUpdate();
//
//            if (rs != 0) {
//                select.setInt(1, vCodigo);
//                ResultSet result = select.executeQuery();
//
//                while (result.next()) {
//                    v.setCodigoProd(result.getInt(1));
//                    v.setQuantidadeItens(result.getInt(2));
//                }
//                atualizaEstoque(v.getQuantidadeItens(), v.getCodigoProd(), "+");
//            }
//
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println("SQL Exception" + e);
//            return false;
//        }
//
        return true;
    }

    public static ArrayList<Venda> getPedidosUsuario(int codigoUser, boolean finalizados) {
        ArrayList<Venda> venda = new ArrayList<>();
        Connection conn = db.obterConexao();

        try {
            String whereClause = finalizados ? " = " : " != ";
            PreparedStatement queryVenda = conn.prepareStatement(
                    "SELECT id_venda, qtd_itens, valor_total, data_venda, nome_status FROM tbl_venda "
                    + "INNER JOIN tbl_usuario ON tbl_venda.fk_usuario = tbl_usuario.id_usuario "
                    + "INNER JOIN tbl_status_venda ON tbl_venda.fk_status = tbl_status_venda.id_status WHERE fk_usuario = ? AND fk_status" + whereClause + "?;"
            );

            queryVenda.setInt(1, codigoUser);
            queryVenda.setInt(2, 3);

            ResultSet rsVenda = queryVenda.executeQuery();
            if (rsVenda != null) {
                while (rsVenda.next()) {
                    Venda v = new Venda();
                    PreparedStatement queryProdutos = conn.prepareStatement(
                            "SELECT nome, descricao, tipo, valor_unidade*qtd_produto AS vl_total, qtd_produto FROM tbl_produtos_venda "
                            + "INNER JOIN tbl_produtos ON tbl_produtos_venda.fk_produto = tbl_produtos.id_produto WHERE fk_venda = ?;"
                    );

                    queryProdutos.setInt(1, rsVenda.getInt(1));
                    ResultSet rsProdutos = queryProdutos.executeQuery();
                    if (rsProdutos != null) {
                        ArrayList<Produto> produtos = new ArrayList<>();
                        while (rsProdutos.next()) {                            
                            Produto prod = new Produto();
                            prod.setNome(rsProdutos.getString(1));
                            prod.setDescricao(rsProdutos.getString(2));
                            prod.setTipo(rsProdutos.getString(3));
                            prod.setValorUnitario(rsProdutos.getDouble(4));
                            prod.setQuantidadeEstoque(rsProdutos.getInt(5));
                            produtos.add(prod);
                        }
                        v.setProdutos(produtos);
                    }
                    
                    v.setCodigoVenda(rsVenda.getInt(1));
                    v.setQuantidadeItens(rsVenda.getInt(2));
                    v.setValorTotal(rsVenda.getDouble(3));
                    v.setData(rsVenda.getString(4));
                    v.setStatus(rsVenda.getString(5));

                    venda.add(v);
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return venda;
    }

    public static void atualizaEstoque(int quantidadeVendida, int codigoProduto, String acao) {
        Connection conn = db.obterConexao();
        try {
            PreparedStatement query = conn.prepareStatement("UPDATE tbl_produtos AS p "
                    + " SET p.qtd_estoque = (p.qtd_estoque " + acao + " ?) WHERE p.id_produto = ?;");

            query.setInt(1, quantidadeVendida);
            query.setInt(2, codigoProduto);

            int rs = query.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e);
        }
    }

}
