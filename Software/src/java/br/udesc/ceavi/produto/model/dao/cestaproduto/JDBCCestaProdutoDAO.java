package br.udesc.ceavi.produto.model.dao.cestaproduto;

import br.udesc.ceavi.core.persistence.Persistence;
import static br.udesc.ceavi.core.persistence.PersistenceType.JDBC;
import br.udesc.ceavi.produto.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import br.udesc.ceavi.produto.model.entidade.Cesta;
import br.udesc.ceavi.produto.model.entidade.CestaProduto;
import br.udesc.ceavi.produto.model.entidade.Produto;
import java.sql.Statement;
import java.util.Calendar;

public class JDBCCestaProdutoDAO implements CestaProdutoDAO {

    @Override
    public boolean inserir(CestaProduto c) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO produto.cesta_produto(\n"
                + "            cesta_id, produto_id, data)\n"
                + "    VALUES (?, ?, ?);";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, c.getCesta().getId());
            stmt.setInt(2, c.getProduto().getId());
            stmt.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            if (stmt.executeUpdate() > 0) {
                ResultSet result = stmt.getGeneratedKeys();
                if (result.next()) {
                    int chave = result.getInt(1);
                    c.setId(chave);
                }
            }
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fechar();
        }
    }

    @Override
    public boolean deletar(int id) {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM produto.cesta_produto\n"
                + " WHERE id = ?;";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fechar();
        }
    }

    @Override
    public boolean atualizar(CestaProduto c) {
        PreparedStatement stmt = null;
        String sql = "UPDATE produto.cesta_produto\n"
                + "   SET id=?, cesta_id=?, produto_id=?, data=?\n"
                + " WHERE id = ?;";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, c.getId());
            stmt.setInt(2, c.getCesta().getId());
            stmt.setInt(3, c.getProduto().getId());
            stmt.setDate(4, new java.sql.Date(c.getCesta().getData().getTime()));
            stmt.setInt(5, c.getId());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fechar();
        }
    }

    @Override
    public CestaProduto pesquisar(int id) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, cesta_id, produto_id, data\n"
                + "  FROM produto.cesta_produto;"
                + "WHERE id = ?;";
        CestaProduto c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Cesta cesta = Persistence.getPersistence(JDBC).getCestaDAO().pesquisar(rs.getInt(2));
            Produto produto = Persistence.getPersistence(JDBC).getProdutoDAO().pesquisar(rs.getInt(3));
            c = new CestaProduto(rs.getInt(1), cesta, produto, rs.getInt(4));
            stmt.close();
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fechar();
        }

    }

    @Override
    public List<CestaProduto> listar() {
        PreparedStatement stmt = null;
        String sql = "SELECT id, cesta_id, produto_id, data\n"
                + "  FROM produto.cesta_produto;";
        ArrayList<CestaProduto> lista = new ArrayList<>();
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            CestaProduto cat = null;
            while (rs.next()) {
                Cesta cesta = Persistence.getPersistence(JDBC).getCestaDAO().pesquisar(rs.getInt(2));
                Produto produto = Persistence.getPersistence(JDBC).getProdutoDAO().pesquisar(rs.getInt(3));
                cat = new CestaProduto(rs.getInt(1), cesta, produto, rs.getInt(4));
                lista.add(cat);
            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
            ;

        }
        System.out.println("CestaProdutos listadas com sucesso!");
        return lista;

    }

}
