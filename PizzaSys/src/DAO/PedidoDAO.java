package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Model.Funcionario;
import Model.ItemPedido;
import Model.Pedido;
import Model.Pizza;

public class PedidoDAO {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static int inserir(Pedido pedido) throws SQLException {

        String sqlPedido =
                "INSERT INTO pedido " +
                "(data, status, funcionario_id, forma_pagamento) " +
                "VALUES (?, ?, ?, ?)";

        String sqlItem =
                "INSERT INTO item_pedido " +
                "(pedido_id, pizza_id, quantidade, preco, observacao, tamanho) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conexao = null;

        try {
            conexao = Conexao.conectar();
            conexao.setAutoCommit(false);

            int idPedido;

            try (PreparedStatement stmtPedido =
                    conexao.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {

                LocalDateTime data =
                        LocalDateTime.parse(pedido.getData(), FORMATO_DATA);

                stmtPedido.setTimestamp(
                        1,
                        Timestamp.valueOf(data)
                );

                stmtPedido.setString(2, pedido.getStatus());
                stmtPedido.setInt(3, pedido.getFuncionario().getId());
                stmtPedido.setInt(4, pedido.getFormaPagamento());

                stmtPedido.executeUpdate();

                try (ResultSet rs = stmtPedido.getGeneratedKeys()) {

                    if (!rs.next()) {
                        throw new SQLException("Não foi possível obter o ID do pedido.");
                    }

                    idPedido = rs.getInt(1);
                }
            }

            inserirItens(conexao, sqlItem, idPedido, pedido);

            conexao.commit();

            return idPedido;

        } catch (SQLException e) {

            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            throw e;

        } finally {

            if (conexao != null) {
                try {
                    conexao.setAutoCommit(true);
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void inserirItens(
            Connection conexao,
            String sql,
            int idPedido,
            Pedido pedido) throws SQLException {

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            for (ItemPedido item : pedido.getItens()) {

                stmt.setInt(1, idPedido);
                stmt.setInt(2, item.getPizza().getId());
                stmt.setInt(3, item.getQuantidade());
                stmt.setDouble(4, item.getPreco());
                stmt.setString(5, item.getObservacao());
                stmt.setInt(6, item.getTamanho());

                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    public static void atualizarPedido(Pedido pedido) throws SQLException {

        String sqlDelete =
                "DELETE FROM item_pedido WHERE pedido_id = ?";

        String sqlItem =
                "INSERT INTO item_pedido " +
                "(pedido_id, pizza_id, quantidade, preco, observacao, tamanho) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        String sqlPedido =
                "UPDATE pedido SET status = ?, forma_pagamento = ? WHERE id = ?";

        Connection conexao = null;

        try {

            conexao = Conexao.conectar();
            conexao.setAutoCommit(false);

            try (PreparedStatement stmt =
                    conexao.prepareStatement(sqlPedido)) {

                stmt.setString(1, pedido.getStatus());
                stmt.setInt(2, pedido.getFormaPagamento());
                stmt.setInt(3, pedido.getId());

                stmt.executeUpdate();
            }

            try (PreparedStatement stmt =
                    conexao.prepareStatement(sqlDelete)) {

                stmt.setInt(1, pedido.getId());
                stmt.executeUpdate();
            }

            inserirItens(conexao, sqlItem, pedido.getId(), pedido);

            conexao.commit();

        } catch (SQLException e) {

            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            throw e;

        } finally {

            if (conexao != null) {
                try {
                    conexao.setAutoCommit(true);
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void atualizarStatus(Pedido pedido) throws SQLException {

        String sql =
                "UPDATE pedido SET status = ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pedido.getStatus());
            stmt.setInt(2, pedido.getId());

            stmt.executeUpdate();
        }
    }

    public static void finalizarPedido(
            Pedido pedido,
            int formaPagamento) throws SQLException {

        String sql =
                "UPDATE pedido " +
                "SET status = ?, forma_pagamento = ? " +
                "WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pedido.getStatus());
            stmt.setInt(2, formaPagamento);
            stmt.setInt(3, pedido.getId());

            stmt.executeUpdate();
        }
    }

    public static void excluir(int id) throws SQLException {

        String sql =
                "DELETE FROM pedido WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static List<Pedido> buscarTodos() throws SQLException {

        List<Pedido> pedidos = new ArrayList<>();

        String sql =
                "SELECT " +
                "p.id AS pedido_id, " +
                "p.data, " +
                "p.status, " +
                "p.forma_pagamento, " +
                "f.id AS funcionario_id, " +
                "f.nome AS funcionario_nome, " +
                "f.login AS funcionario_login, " +
                "f.senha AS funcionario_senha, " +
                "f.funcao AS funcionario_funcao, " +
                "f.salario AS funcionario_salario " +
                "FROM pedido p " +
                "INNER JOIN funcionario f ON p.funcionario_id = f.id " +
                "ORDER BY p.id";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Pedido pedido = criarPedido(rs);

                carregarItens(conexao, pedido);

                pedidos.add(pedido);
            }
        }

        return pedidos;
    }

    public static List<Pedido> buscarPorStatus(String status)
            throws SQLException {

        List<Pedido> pedidos = new ArrayList<>();

        String sql =
                "SELECT " +
                "p.id AS pedido_id, " +
                "p.data, " +
                "p.status, " +
                "p.forma_pagamento, " +
                "f.id AS funcionario_id, " +
                "f.nome AS funcionario_nome, " +
                "f.login AS funcionario_login, " +
                "f.senha AS funcionario_senha, " +
                "f.funcao AS funcionario_funcao, " +
                "f.salario AS funcionario_salario " +
                "FROM pedido p " +
                "INNER JOIN funcionario f ON p.funcionario_id = f.id " +
                "WHERE p.status = ? " +
                "ORDER BY p.id";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Pedido pedido = criarPedido(rs);

                    carregarItens(conexao, pedido);

                    pedidos.add(pedido);
                }
            }
        }

        return pedidos;
    }

    public static Pedido buscarPorId(int id) throws SQLException {

        String sql =
                "SELECT " +
                "p.id AS pedido_id, " +
                "p.data, " +
                "p.status, " +
                "p.forma_pagamento, " +
                "f.id AS funcionario_id, " +
                "f.nome AS funcionario_nome, " +
                "f.login AS funcionario_login, " +
                "f.senha AS funcionario_senha, " +
                "f.funcao AS funcionario_funcao, " +
                "f.salario AS funcionario_salario " +
                "FROM pedido p " +
                "INNER JOIN funcionario f ON p.funcionario_id = f.id " +
                "WHERE p.id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Pedido pedido = criarPedido(rs);

                    carregarItens(conexao, pedido);

                    return pedido;
                }
            }
        }

        return null;
    }

    private static Pedido criarPedido(ResultSet rs)
            throws SQLException {

        int funcionarioId = rs.getInt("funcionario_id");

        Funcionario funcionario = new Funcionario(
                funcionarioId,
                rs.getString("funcionario_nome"),
                rs.getString("funcionario_login"),
                rs.getString("funcionario_senha"),
                rs.getInt("funcionario_funcao"),
                rs.getDouble("funcionario_salario")
        );

        Timestamp timestamp = rs.getTimestamp("data");

        String data = timestamp
                .toLocalDateTime()
                .format(FORMATO_DATA);

        Pedido pedido = new Pedido(
                rs.getInt("pedido_id"),
                data,
                funcionario
        );

        pedido.setStatus(rs.getString("status"));
        pedido.setFormaPagamento(
                rs.getInt("forma_pagamento")
        );

        return pedido;
    }

    private static void carregarItens(
            Connection conexao,
            Pedido pedido) throws SQLException {

        String sql =
                "SELECT " +
                "ip.quantidade, " +
                "ip.preco, " +
                "ip.observacao, " +
                "ip.tamanho, " +
                "pz.id AS pizza_id, " +
                "pz.nome AS pizza_nome, " +
                "pz.preco AS pizza_preco, " +
                "pz.descricao AS pizza_descricao, " +
                "pz.imagem AS pizza_imagem " +
                "FROM item_pedido ip " +
                "INNER JOIN pizza pz ON ip.pizza_id = pz.id " +
                "WHERE ip.pedido_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getId());

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    byte[] imagem = rs.getBytes("pizza_imagem");

                    Pizza pizza = new Pizza(
                            rs.getInt("pizza_id"),
                            rs.getString("pizza_nome"),
                            rs.getDouble("pizza_preco"),
                            rs.getString("pizza_descricao"),
                            imagem
                    );

                    ItemPedido item = new ItemPedido(
                            pizza,
                            rs.getInt("quantidade"),
                            rs.getDouble("preco"),
                            rs.getString("observacao"),
                            rs.getInt("tamanho")
                    );

                    pedido.adicionarItem(item);
                }
            }
        }
    }
}