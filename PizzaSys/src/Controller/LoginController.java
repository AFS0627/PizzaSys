package Controller;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Funcionario;
import View.TelaBanco;
import View.TelaLogin;
import View.TelaPrincipal;

public class LoginController {

	public static void iniciarSistema() {

		Funcionario admin = new Funcionario(1, "Administrador", "admin", "3", 3, 2500);
		Funcionario admin1 = new Funcionario(2, "Administrador", "admin", "2", 2, 2500);
		Funcionario admin2 = new Funcionario(3, "Administrador", "admin", "1", 1, 2500);

		GeralController.getFuncionarios().add(admin);
		GeralController.getFuncionarios().add(admin1);
		GeralController.getFuncionarios().add(admin2);

		TelaLogin telaLogin = new TelaLogin();
		telaLogin.setVisible(true);
	}

	public static void realizarLogin(String login, String senha, TelaLogin telaLogin) {

		for (Funcionario funcionario : GeralController.getFuncionarios()) {

			if (funcionario.getLogin().equals(login) && funcionario.getSenha().equals(senha)) {

				JOptionPane.showMessageDialog(null, "Bem-vindo, " + funcionario.getNome() + "!");

				System.out.println("Deu certo!");

				TelaPrincipal telaprincipal = new TelaPrincipal(funcionario);
				telaprincipal.setVisible(true);

				telaLogin.dispose();

				return;
			}
		}

		JOptionPane.showMessageDialog(null, "Login ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
	}

	public static void IniciarTelaBanco() {
		TelaBanco telabanco = new TelaBanco();
		telabanco.setVisible(true);
	}
}