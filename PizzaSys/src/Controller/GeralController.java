package Controller;

import javax.swing.JOptionPane;

import Model.Funcionario;
import Model.Notificacao;

public class GeralController {

	public static void sair() {

		int resposta = JOptionPane.showConfirmDialog(
				null,
				"Tem certeza que deseja sair?",
				"Sair",
				JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public static int QntdNotificacao(Funcionario funcionario) {

		if (funcionario == null) {
			return 0;
		}

		int quantidade = 0;

		for (Notificacao notificacao : funcionario.getNotificacoes()) {

			if (!notificacao.isLida()) {
				quantidade++;
			}
		}

		return quantidade;
	}

	public static void mostrarSemPermissao(java.awt.Component parent) {

		JOptionPane.showMessageDialog(
				parent,
				"Você não tem permissão para acessar!",
				"Erro",
				JOptionPane.ERROR_MESSAGE);
	}
}