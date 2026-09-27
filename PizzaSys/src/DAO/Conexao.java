package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Model.ConfiguracaoBanco;

public class Conexao {

	public static Connection conectar() throws SQLException {

		try {

			ConfiguracaoBanco configuracao = ConfiguracaoBancoDAO.carregar();

			String url = "jdbc:mysql://" + configuracao.getServidor() + "/" + configuracao.getBanco()
					+ "?useSSL=false&serverTimezone=America/Sao_Paulo";

			return DriverManager.getConnection(url, configuracao.getUsuario(), configuracao.getSenha());

		} catch (Exception e) {

			throw new SQLException("Não foi possível carregar as configurações do banco.", e);
		}
	}
}