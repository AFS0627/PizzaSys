package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import Model.ConfiguracaoBanco;

public class ConfiguracaoBancoDAO {

	private static final String PASTA_CONFIG = "config";
	private static final String ARQUIVO_CONFIG = "config/banco.properties";

	public static ConfiguracaoBanco carregar() throws IOException {

		File arquivo = new File(ARQUIVO_CONFIG);

		if (!arquivo.exists()) {
			return criarConfiguracaoInicial();
		}

		Properties propriedades = new Properties();

		try (FileInputStream entrada = new FileInputStream(arquivo)) {
			propriedades.load(entrada);
		}

		String servidor = propriedades.getProperty("servidor", "localhost:3306");
		String usuario = propriedades.getProperty("usuario", "root");
		String senha = propriedades.getProperty("senha", "");
		String banco = propriedades.getProperty("banco", "pizzasys");

		return new ConfiguracaoBanco(servidor, usuario, senha, banco);
	}

	public static void salvar(ConfiguracaoBanco configuracao) throws IOException {

		File pasta = new File(PASTA_CONFIG);

		if (!pasta.exists()) {
			pasta.mkdirs();
		}

		Properties propriedades = new Properties();

		propriedades.setProperty("servidor", configuracao.getServidor());

		propriedades.setProperty("usuario", configuracao.getUsuario());

		propriedades.setProperty("senha", configuracao.getSenha());

		propriedades.setProperty("banco", configuracao.getBanco());

		try (FileOutputStream saida = new FileOutputStream(ARQUIVO_CONFIG)) {

			propriedades.store(saida, "Configuracao do banco de dados - PizzaSys");
		}
	}

	private static ConfiguracaoBanco criarConfiguracaoInicial() throws IOException {

		ConfiguracaoBanco configuracao = new ConfiguracaoBanco("localhost:3306", "root", "1234", "pizzasys");

		salvar(configuracao);

		return configuracao;
	}
}