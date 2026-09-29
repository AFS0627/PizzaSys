package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Notificacao;

public class NotificacaoDAO {

	public static void inserir(int funcionarioId, String titulo, String mensagem) throws SQLException {

		String sql = "INSERT INTO notificacao " + "(funcionario_id, titulo, mensagem, lida) "
				+ "VALUES (?, ?, ?, FALSE)";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, funcionarioId);
			stmt.setString(2, titulo);
			stmt.setString(3, mensagem);

			stmt.executeUpdate();
		}
	}

	public static List<Notificacao> buscarPorFuncionario(int funcionarioId) throws SQLException {

		List<Notificacao> notificacoes = new ArrayList<Notificacao>();

		String sql = "SELECT id, titulo, mensagem, lida " + "FROM notificacao " + "WHERE funcionario_id = ? "
				+ "ORDER BY id DESC";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, funcionarioId);

			try (ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {

					Notificacao notificacao = new Notificacao(rs.getInt("id"), rs.getString("titulo"),
							rs.getString("mensagem"));

					if (rs.getBoolean("lida")) {
						notificacao.marcarComoLida();
					}

					notificacoes.add(notificacao);
				}
			}
		}

		return notificacoes;
	}

	public static void marcarComoLida(int notificacaoId) throws SQLException {

		String sql = "UPDATE notificacao " + "SET lida = TRUE " + "WHERE id = ?";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, notificacaoId);
			stmt.executeUpdate();
		}
	}

	public static void marcarComoNaoLida(int notificacaoId) throws SQLException {

		String sql = "UPDATE notificacao " + "SET lida = FALSE " + "WHERE id = ?";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, notificacaoId);
			stmt.executeUpdate();
		}
	}

	public static int contarNaoLidas(int funcionarioId) throws SQLException {

		String sql = "SELECT COUNT(*) " + "FROM notificacao " + "WHERE funcionario_id = ? " + "AND lida = FALSE";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, funcionarioId);

			try (ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}

		return 0;
	}

	public static void excluir(int notificacaoId) throws SQLException {

		String sql = "DELETE FROM notificacao " + "WHERE id = ?";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, notificacaoId);
			stmt.executeUpdate();
		}
	}

	public static void excluirTodas(int funcionarioId) throws SQLException {
		String sql = "DELETE FROM notificacao WHERE funcionario_id = ?";
		try (Connection connection = Conexao.conectar();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, funcionarioId);
			statement.executeUpdate();
		}
	}
}