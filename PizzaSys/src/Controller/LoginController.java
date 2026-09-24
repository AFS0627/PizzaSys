package Controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Funcionario;
import View.TelaBanco;
import View.TelaLogin;

public class LoginController {

	private static List<Funcionario> funcionarios = new ArrayList<>();

	public static void iniciarSistema() {

		Funcionario admin = new Funcionario(1, "Administrador", "admin", "admin", 3);

		funcionarios.add(admin);

		TelaLogin telaLogin = new TelaLogin();
		telaLogin.setVisible(true);
	}

	public static void realizarLogin(String login, String senha) {

		for (Funcionario funcionario : funcionarios) {

			if (funcionario.getLogin().equals(login) && funcionario.getSenha().equals(senha)) {

				JOptionPane.showMessageDialog(null, "Bem-vindo, " + funcionario.getNome() + "!");

				System.out.println("Deu certo!");

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