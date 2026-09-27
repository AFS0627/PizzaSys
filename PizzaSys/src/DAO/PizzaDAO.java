package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Pizza;

public class PizzaDAO {

    public static int inserir(Pizza pizza) throws SQLException {

        String sql =
                "INSERT INTO pizza " +
                "(nome, preco, descricao, imagem) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pizza.getNome());
            stmt.setDouble(2, pizza.getPreco());
            stmt.setString(3, pizza.getDescricao());

            if (pizza.getImagem() != null) {
                stmt.setBytes(4, pizza.getImagem());
            } else {
                stmt.setNull(4, java.sql.Types.LONGVARBINARY);
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }

                throw new SQLException(
                        "Não foi possível obter o ID da pizza."
                );
            }
        }
    }

    public static void atualizar(Pizza pizza) throws SQLException {

        String sql =
                "UPDATE pizza " +
                "SET nome = ?, preco = ?, descricao = ?, imagem = ? " +
                "WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pizza.getNome());
            stmt.setDouble(2, pizza.getPreco());
            stmt.setString(3, pizza.getDescricao());

            if (pizza.getImagem() != null) {
                stmt.setBytes(4, pizza.getImagem());
            } else {
                stmt.setNull(4, java.sql.Types.LONGVARBINARY);
            }

            stmt.setInt(5, pizza.getId());

            stmt.executeUpdate();
        }
    }

    public static void excluir(int id) throws SQLException {

        String sql = "DELETE FROM pizza WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    public static List<Pizza> buscarTodos() throws SQLException {

        List<Pizza> pizzas = new ArrayList<Pizza>();

        String sql =
                "SELECT id, nome, preco, descricao, imagem " +
                "FROM pizza " +
                "ORDER BY id";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Pizza pizza = criarPizza(rs);

                pizzas.add(pizza);
            }
        }

        return pizzas;
    }

    public static Pizza buscarPorId(int id) throws SQLException {

        String sql =
                "SELECT id, nome, preco, descricao, imagem " +
                "FROM pizza " +
                "WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return criarPizza(rs);
                }
            }
        }

        return null;
    }

    private static Pizza criarPizza(ResultSet rs) throws SQLException {

        return new Pizza(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getDouble("preco"),
                rs.getString("descricao"),
                rs.getBytes("imagem")
        );
    }
}