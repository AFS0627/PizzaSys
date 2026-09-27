package Controller;

import DAO.ConfiguracaoBancoDAO;
import Model.ConfiguracaoBanco;

public class BancoController {

	public static ConfiguracaoBanco carregarConfiguracao() {

		try {

			return ConfiguracaoBancoDAO.carregar();

		} catch (Exception e) {

			e.printStackTrace();

			return null;
		}
	}

	public static String salvarConfiguracao(String servidor, String usuario, String senha, String banco) {

		if (servidor == null || servidor.trim().isEmpty()) {
			return "Informe o servidor.";
		}

		if (usuario == null || usuario.trim().isEmpty()) {
			return "Informe o usuário.";
		}

		if (banco == null || banco.trim().isEmpty()) {
			return "Informe o banco de dados.";
		}

		ConfiguracaoBanco configuracao = new ConfiguracaoBanco(servidor.trim(), usuario.trim(),
				senha == null ? "" : senha, banco.trim());

		try {

			ConfiguracaoBancoDAO.salvar(configuracao);

			return null;

		} catch (Exception e) {

			e.printStackTrace();

			return "Não foi possível salvar as configurações.";
		}
	}
}