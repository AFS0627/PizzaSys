package Controller;

import java.awt.Window;

import Model.Funcionario;
import Model.Notificacao;

public class GeralController {

	
	
	public static void sair() {
		System.exit(0);
	}
	public static int QntdNotificacao(Funcionario funcionario) {
		int quantidade = 0;

		for (Notificacao notificacao : funcionario.getNotificacoes()) {

			if (!notificacao.isLida()) {
				quantidade++;
			}
		}

		return quantidade;
	}
}
