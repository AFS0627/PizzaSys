package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.NotificacaoDAO;
import Model.Funcionario;
import Model.Notificacao;

public class NotificacoesController {

	public static List<Notificacao> getNotificacoes(Funcionario funcionario) {

		if (funcionario == null) {
			return new ArrayList<Notificacao>();
		}

		try {
			return NotificacaoDAO.buscarPorFuncionario(funcionario.getId());
		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível carregar as notificações.", e);
		}
	}

	public static void enviarNotificacao(int funcionarioId, String titulo, String mensagem) {

		if (titulo == null || titulo.trim().isEmpty()) {
			return;
		}

		if (mensagem == null || mensagem.trim().isEmpty()) {
			return;
		}

		try {
			NotificacaoDAO.inserir(funcionarioId, titulo.trim(), mensagem.trim());
		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível enviar a notificação.", e);
		}
	}

	public static void enviarParaAdministradores(String titulo, String mensagem) {

		List<Funcionario> funcionarios = FuncionariosController.getFuncionarios();

		for (Funcionario funcionario : funcionarios) {

			if (funcionario.getFuncao() == 3) {

				enviarNotificacao(funcionario.getId(), titulo, mensagem);
			}
		}
	}

	public static void marcarComoLida(Notificacao notificacao) {

		if (notificacao == null) {
			return;
		}

		try {
			NotificacaoDAO.marcarComoLida(notificacao.getId());

			notificacao.marcarComoLida();

		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível marcar a notificação como lida.", e);
		}
	}

	public static void marcarComoNaoLida(Notificacao notificacao) {

		if (notificacao == null) {
			return;
		}

		try {
			NotificacaoDAO.marcarComoNaoLida(notificacao.getId());

			notificacao.marcarComoNaoLida();

		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível marcar a notificação como não lida.", e);
		}
	}

	public static int quantidadeNaoLidas(Funcionario funcionario) {

		if (funcionario == null) {
			return 0;
		}

		try {
			return NotificacaoDAO.contarNaoLidas(funcionario.getId());
		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível contar as notificações.", e);
		}
	}

	public static void excluir(Notificacao notificacao) {

		if (notificacao == null) {
			return;
		}

		try {
			NotificacaoDAO.excluir(notificacao.getId());
		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível excluir a notificação.", e);
		}
	}

	public static void excluirTodas(Funcionario funcionario) {
		if (funcionario == null) {
			return;
		}
		try {
			NotificacaoDAO.excluirTodas(funcionario.getId());
		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível excluir as notificações.", e);
		}
	}
}