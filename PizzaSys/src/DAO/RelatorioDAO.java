package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Model.Funcionario;
import Model.Relatorio;

public class RelatorioDAO {

	public static void inserir(Relatorio relatorio) throws SQLException {
		String sql = "INSERT INTO movimentacao " + "(funcionario_id, acao, pedido_id, data_hora) "
				+ "VALUES (?, ?, ?, ?)";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			if (relatorio.getFuncionario() != null) {
				stmt.setInt(1, relatorio.getFuncionario().getId());
			} else {
				stmt.setNull(1, java.sql.Types.INTEGER);
			}

			stmt.setString(2, relatorio.getAcao());

			if (relatorio.getIdPedido() > 0) {
				stmt.setInt(3, relatorio.getIdPedido());
			} else {
				stmt.setNull(3, java.sql.Types.INTEGER);
			}

			stmt.setTimestamp(4, Timestamp.valueOf(relatorio.getDataHora()));

			stmt.executeUpdate();
		}
	}

	public static List<Relatorio> buscarTodos() throws SQLException {
		List<Relatorio> movimentacoes = new ArrayList<>();

		String sql = "SELECT " + "m.id AS movimentacao_id, " + "m.acao, " + "m.pedido_id, " + "m.data_hora, "
				+ "f.id AS funcionario_id, " + "f.nome AS funcionario_nome, " + "f.login AS funcionario_login, "
				+ "f.senha AS funcionario_senha, " + "f.funcao AS funcionario_funcao, "
				+ "f.salario AS funcionario_salario " + "FROM movimentacao m "
				+ "LEFT JOIN funcionario f ON m.funcionario_id = f.id " + "ORDER BY m.data_hora DESC, m.id DESC";

		try (Connection conexao = Conexao.conectar();
				PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				movimentacoes.add(criarRelatorio(rs));
			}
		}

		return movimentacoes;
	}

	public static List<Relatorio> buscarPorFuncionario(int funcionarioId) throws SQLException {
		List<Relatorio> movimentacoes = new ArrayList<>();

		String sql = "SELECT " + "m.id AS movimentacao_id, " + "m.acao, " + "m.pedido_id, " + "m.data_hora, "
				+ "f.id AS funcionario_id, " + "f.nome AS funcionario_nome, " + "f.login AS funcionario_login, "
				+ "f.senha AS funcionario_senha, " + "f.funcao AS funcionario_funcao, "
				+ "f.salario AS funcionario_salario " + "FROM movimentacao m "
				+ "LEFT JOIN funcionario f ON m.funcionario_id = f.id " + "WHERE m.funcionario_id = ? "
				+ "ORDER BY m.data_hora DESC, m.id DESC";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, funcionarioId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					movimentacoes.add(criarRelatorio(rs));
				}
			}
		}

		return movimentacoes;
	}

	private static Relatorio criarRelatorio(ResultSet rs) throws SQLException {
		Funcionario funcionario = null;

		int funcionarioId = rs.getInt("funcionario_id");

		if (!rs.wasNull()) {
			funcionario = new Funcionario(funcionarioId, rs.getString("funcionario_nome"),
					rs.getString("funcionario_login"), rs.getString("funcionario_senha"),
					rs.getInt("funcionario_funcao"), rs.getDouble("funcionario_salario"));
		}

		int idPedido = rs.getInt("pedido_id");

		if (rs.wasNull()) {
			idPedido = 0;
		}

		Timestamp timestamp = rs.getTimestamp("data_hora");

		return new Relatorio(rs.getInt("movimentacao_id"), funcionario, rs.getString("acao"), idPedido,
				timestamp.toLocalDateTime());
	}
}