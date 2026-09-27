package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Funcionario;

public class FuncionarioDAO {

	public static Funcionario realizarLogin(String login, String senha) throws SQLException {
		String sql = "SELECT id, nome, login, senha, funcao, salario " + "FROM funcionario "
				+ "WHERE login = ? AND senha = ?";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, login);
			stmt.setString(2, senha);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return criarFuncionario(rs);
				}
			}
		}

		return null;
	}

	public static List<Funcionario> buscarTodos() throws SQLException {
		List<Funcionario> funcionarios = new ArrayList<>();

		String sql = "SELECT id, nome, login, senha, funcao, salario " + "FROM funcionario " + "ORDER BY id";

		try (Connection conexao = Conexao.conectar();
				PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				funcionarios.add(criarFuncionario(rs));
			}
		}

		return funcionarios;
	}

	public static Funcionario buscarPorId(int id) throws SQLException {
		String sql = "SELECT id, nome, login, senha, funcao, salario " + "FROM funcionario " + "WHERE id = ?";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return criarFuncionario(rs);
				}
			}
		}

		return null;
	}

	public static int inserir(Funcionario funcionario) throws SQLException {
		String sql = "INSERT INTO funcionario " + "(nome, login, senha, funcao, salario) " + "VALUES (?, ?, ?, ?, ?)";

		try (Connection conexao = Conexao.conectar();
				PreparedStatement stmt = conexao.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getLogin());
			stmt.setString(3, funcionario.getSenha());
			stmt.setInt(4, funcionario.getFuncao());
			stmt.setDouble(5, funcionario.getSalario());

			stmt.executeUpdate();

			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}

		throw new SQLException("Não foi possível obter o ID do funcionário.");
	}

	public static void atualizar(Funcionario funcionario) throws SQLException {
		String sql = "UPDATE funcionario SET " + "nome = ?, " + "login = ?, " + "senha = ?, " + "funcao = ?, "
				+ "salario = ? " + "WHERE id = ?";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getLogin());
			stmt.setString(3, funcionario.getSenha());
			stmt.setInt(4, funcionario.getFuncao());
			stmt.setDouble(5, funcionario.getSalario());
			stmt.setInt(6, funcionario.getId());

			stmt.executeUpdate();
		}
	}

	public static void excluir(int id) throws SQLException {
		String sql = "DELETE FROM funcionario WHERE id = ?";

		try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	private static Funcionario criarFuncionario(ResultSet rs) throws SQLException {
		return new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("login"), rs.getString("senha"),
				rs.getInt("funcao"), rs.getDouble("salario"));
	}
}