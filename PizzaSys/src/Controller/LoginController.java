package Controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import DAO.FuncionarioDAO;
import Model.Funcionario;
import View.TelaBanco;
import View.TelaLogin;
import View.TelaPrincipal;
import View.TelaSenha;

public class LoginController {

	public static void iniciarSistema() {

		TelaLogin telaLogin = new TelaLogin();
		telaLogin.setVisible(true);
	}

	public static void realizarLogin(String login, String senha, TelaLogin telaLogin) {

		if (login.trim().isEmpty() || senha.trim().isEmpty()) {
			JOptionPane.showMessageDialog(telaLogin, "Informe o login e a senha.", "Atenção",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {

			Funcionario funcionario = FuncionarioDAO.realizarLogin(login, senha);

			if (funcionario != null) {

				JOptionPane.showMessageDialog(telaLogin, "Bem-vindo, " + funcionario.getNome() + "!");

				TelaPrincipal telaPrincipal = new TelaPrincipal(funcionario);
				telaPrincipal.setVisible(true);

				telaLogin.dispose();

			} else {

				JOptionPane.showMessageDialog(telaLogin, "Login ou senha incorretos.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(telaLogin,
					"Não foi possível conectar ao banco de dados.\n\n" + "Verifique se o MySQL está funcionando.",
					"Erro de conexão", JOptionPane.ERROR_MESSAGE);

			System.err.println("Erro ao conectar ao banco:");
			e.printStackTrace();
		}
	}

	public static void IniciarTelaBanco() {

		TelaBanco telaBanco = new TelaBanco();
		telaBanco.setVisible(true);
	}
	
	public static void IniciarTelaSenha() {

		TelaSenha telaSenha = new TelaSenha();
		telaSenha.setVisible(true);
	}

	
	
}