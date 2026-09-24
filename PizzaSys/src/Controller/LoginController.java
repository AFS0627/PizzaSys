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
	

	private static List<Funcionario> funcionarios = new ArrayList<>();

	public static void iniciarSistema() {

		Funcionario admin = new Funcionario(1, "Administrador", "admin", "admin", 3, 2500);

		funcionarios.add(admin);

		TelaLogin telaLogin = new TelaLogin();
		telaLogin.setVisible(true);
	}

	public static void realizarLogin(String login, String senha, TelaLogin telaLogin) {

		for (Funcionario funcionario : funcionarios) {

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