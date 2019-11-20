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

    public static boolean salvarVenda(Venda v) {
        int idVenda = 0;
        Connection conn = db.obterConexao();

        try {
            PreparedStatement queryVenda = conn.prepareStatement("INSERT INTO"
                    + " tbl_venda(codigo_venda, qtd_total, valor_frete, valor_total, data_venda, fk_endereco, fk_usuario, fk_status, fk_pagamento)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            queryVenda.setString(1, v.getCodigoVenda());
            queryVenda.setInt(2, v.getQuantidadeItens());
            queryVenda.setDouble(3, v.getValorFrete());
            queryVenda.setDouble(4, v.getValorTotal());
            queryVenda.setString(5, v.getData());
            queryVenda.setInt(6, v.getIdEndereco());
            queryVenda.setInt(7, v.getCodigoUsuario());
            queryVenda.setInt(8, v.getIdStatus());
            queryVenda.setInt(9, v.getIdPagamento());

            queryVenda.executeUpdate();

            ResultSet rs = queryVenda.getGeneratedKeys();
            if (rs.next()) {
                idVenda = rs.getInt(1);
            }

            PreparedStatement queryProduto;
            for (Produto p : v.getProdutos()) {
                p.setCodigoVenda(idVenda);

                queryProduto = conn.prepareStatement("INSERT INTO"
                        + " tbl_produtos_venda(fk_venda, fk_produto, qtd_produto)"
                        + " VALUES (?, ?, ?);");

                queryProduto.setInt(1, p.getCodigoVenda());
                queryProduto.setInt(2, p.getCodigo());
                queryProduto.setInt(3, p.getQuantidadeEstoque());

                queryProduto.executeUpdate();
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e);
            return false;
        }

        return true;
    }

    public static ArrayList<Venda> getPedidosUsuario(int codigoUser, boolean finalizados) {
        ArrayList<Venda> venda = new ArrayList<>();
        Connection conn = db.obterConexao();

        try {
            String whereClause = finalizados ? " = " : " != ";
            PreparedStatement queryVenda = conn.prepareStatement(
                    "SELECT v.id_venda, v.codigo_venda, v.qtd_total, v.valor_total, v.data_venda,"
                    + " status.nome_status, info.nome, numero_pagamento, v.valor_frete FROM tbl_venda as v"
                    + " INNER JOIN tbl_status_venda AS status ON v.fk_status = status.id_status"
                    + " INNER jOIN tbl_pagamento_usuario ON v.fk_pagamento = tbl_pagamento_usuario.id_pagamento"
                    + " INNER JOIN tbl_info_pagamentos AS info ON tbl_pagamento_usuario.fk_info_pagamento = info.id_info_pagamento"
                    + " WHERE v.fk_usuario = ? AND v.fk_status" + whereClause + "?;"
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

                    v.setIdVenda(rsVenda.getInt(1));
                    v.setCodigoVenda(rsVenda.getString(2));
                    v.setQuantidadeItens(rsVenda.getInt(3));
                    v.setValorTotal(rsVenda.getDouble(4));
                    v.setData(rsVenda.getString(5));
                    v.setStatus(rsVenda.getString(6));
                    v.setTipoPagamento(rsVenda.getString(7));
                    v.setCodigoPagamento(rsVenda.getString(8));
                    v.setValorFrete(rsVenda.getDouble(9));

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
