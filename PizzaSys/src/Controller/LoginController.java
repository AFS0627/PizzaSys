package Controller;

import javax.swing.JOptionPane;

import Model.Funcionario;
import View.TelaBanco;
import View.TelaLogin;
import View.TelaPrincipal;

public class LoginController {

	public static void iniciarSistema() {

		Funcionario admin = new Funcionario(1, "Administrador", "admin", "3", 3, 2500);

		Funcionario admin1 = new Funcionario(2, "Gerente", "admin", "2", 2, 2500);

		Funcionario admin2 = new Funcionario(3, "Atendente", "admin", "1", 1, 2500);

		FuncionariosController.getFuncionarios().add(admin);
		FuncionariosController.getFuncionarios().add(admin1);
		FuncionariosController.getFuncionarios().add(admin2);

		TelaLogin telaLogin = new TelaLogin();
		telaLogin.setVisible(true);
	}

	public static void realizarLogin(String login, String senha, TelaLogin telaLogin) {

		for (Funcionario funcionario : FuncionariosController.getFuncionarios()) {

			if (funcionario.getLogin().equals(login) && funcionario.getSenha().equals(senha)) {

				JOptionPane.showMessageDialog(null, "Bem-vindo, " + funcionario.getNome() + "!");

				System.out.println("Deu certo!");

				TelaPrincipal telaPrincipal = new TelaPrincipal(funcionario);

				telaPrincipal.setVisible(true);

				telaLogin.dispose();

				return;
			}
		}

		JOptionPane.showMessageDialog(null, "Login ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
	}

	public static void IniciarTelaBanco() {

		TelaBanco telaBanco = new TelaBanco();

		telaBanco.setVisible(true);
	}
}