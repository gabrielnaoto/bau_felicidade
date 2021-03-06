package br.udesc.ceavi.produto.model.dao.categoria;

import br.udesc.ceavi.produto.util.Conexao;
import br.udesc.ceavi.produto.model.entidade.Categoria;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCCategoriaDAO implements CategoriaDAO {

    @Override
    public boolean inserir(Categoria c) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO produto.categoria(\n"
                + "            descricao)\n"
                + "    VALUES (?);";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getDescricao());
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
        String sql = "DELETE FROM produto.categoria\n"
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
    public boolean atualizar(Categoria c) {
        PreparedStatement stmt = null;
        String sql = "UPDATE produto.categoria\n"
                + "   SET id=?, descricao=?\n"
                + " WHERE id = ?;";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, c.getId());
            stmt.setString(2, c.getDescricao());
            stmt.setInt(3, c.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fechar();
        }
    }

    @Override
    public Categoria pesquisar(int id) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao\n"
                + "  FROM produto.categoria"
                + " WHERE id = ?;";
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            c = new Categoria(rs.getInt(1), rs.getString(2));
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
    public Categoria pesquisar(String descricao) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao\n"
                + "  FROM produto.categoria\n"
                + "WHERE LOWER(descricao) = LOWER(?);";
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                c = new Categoria(rs.getInt("id"), rs.getString("descricao"));
            }
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fechar();
        }

    }

    @Override
    public List<Categoria> listar() {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao\n"
                + "  FROM produto.categoria;";
        ArrayList<Categoria> lista = new ArrayList<>();
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Categoria cat = null;
            while (rs.next()) {
                cat = new Categoria(rs.getInt("categoria_id"), rs.getString("descricao"));
                lista.add(cat);

            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Categorias listadas com sucesso!");
        return lista;

    }

    @Override
    public List<Categoria> getFromCesta(int cestaid) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao\n"
                + "FROM produto.categoria\n"
                + "WHERE id IN (\n"
                + "SELECT categoria_id\n"
                + "FROM produto.cesta c JOIN produto.cesta_categoria cc ON c.id = cc.cesta_id\n"
                + "WHERE c.id = ?)";
        ArrayList<Categoria> lista = new ArrayList<>();
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, cestaid);
            ResultSet rs = stmt.executeQuery();
            Categoria cat = null;
            while (rs.next()) {
                cat = new Categoria(rs.getInt("id"), rs.getString("descricao"));
                lista.add(cat);
            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Categorias listadas com sucesso!");
        return lista;

    }

    @Override
    public int getQuantidade() {
        Statement st = null;
        ResultSet rs = null;
        int numCol = 0;
        String sql = "SELECT count(id) as quantCat FROM produto.categoria;";
        try {
            st = Conexao.getConexao(1).createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            numCol = rs.getInt("quantCat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numCol;
    }

    @Override
    public List<Categoria> pesquisarVarios(String query) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao\n"
                + "  FROM produto.categoria where LOWER(descricao) LIKE LOWER(?);";
        ArrayList<Categoria> lista = new ArrayList<>();
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            query = "%" + query + "%";
            stmt.setString(1, query);
            ResultSet rs = stmt.executeQuery();
            Categoria cat = null;
            while (rs.next()) {
                cat = new Categoria(rs.getInt("id"), rs.getString("descricao"));
                lista.add(cat);

            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);

        }
        System.out.println("Categorias listadas com sucesso!");
        return lista;
    }

    @Override
    public List<Categoria> listarPodemInserir() {
        PreparedStatement stmt = null;
        String sql = "select id, descricao\n"
                + "from produto.categoria \n"
                + "where id in\n"
                + "(select categoria_id\n"
                + "from produto.cesta_categoria\n"
                + "where data <= (current_date - interval '1 year'))";
        ArrayList<Categoria> lista = new ArrayList<>();
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Categoria cat = null;
            while (rs.next()) {
                cat = new Categoria(rs.getInt("categoria_id"), rs.getString("descricao"));
                lista.add(cat);

            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Categorias listadas com sucesso!");
        return lista;
    }

}
