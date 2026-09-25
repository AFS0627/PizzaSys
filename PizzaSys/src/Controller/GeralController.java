package Controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Model.Funcionario;
import Model.Notificacao;
import View.TelaFuncionarios;
import View.TelaIncluirFuncionario;
import View.TelaPrincipal;

public class GeralController {

	private static List<Funcionario> funcionarios = new ArrayList<>();
	private static int proximoId = 1;

	public static List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	// =========================================================
	// SAIR
	// =========================================================

	public static void sair() {

		int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Sair",
				JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	// =========================================================
	// NOTIFICAÇÕES
	// =========================================================

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

	// =========================================================
	// CARREGAR FUNCIONÁRIOS NA TABELA
	// =========================================================

	public static void carregarFuncionarios(DefaultTableModel modeloTabela) {

		modeloTabela.setRowCount(0);

		for (Funcionario funcionario : GeralController.getFuncionarios()) {

			modeloTabela.addRow(new Object[] { funcionario.getId(), funcionario.getNome(), funcionario.getLogin(),
					nomeFuncao(funcionario.getFuncao()), String.format("R$ %.2f", funcionario.getSalario()) });
		}
	}

	// =========================================================
	// INCLUIR
	// =========================================================

	public static void IniciarTelaIncluirFuncionario(DefaultTableModel modeloTabela) {

		TelaIncluirFuncionario incluir = new TelaIncluirFuncionario(modeloTabela);
		incluir.setVisible(true);

	}

	public static void cadastrarFuncionario(TelaIncluirFuncionario tela, DefaultTableModel modeloTabela, String nome,
			String login, String senha, int funcao, String salarioTexto) {

		if (nome.trim().isEmpty()) {
			tela.mostrarErro("Digite o nome do funcionário.");
			return;
		}

		if (login.trim().isEmpty()) {
			tela.mostrarErro("Digite o login.");
			return;
		}

		if (senha.trim().isEmpty()) {
			tela.mostrarErro("Digite a senha.");
			return;
		}

		if (salarioTexto.trim().isEmpty()) {
			tela.mostrarErro("Digite o salário.");
			return;
		}

		double salario;

		try {

			salario = Double.parseDouble(salarioTexto.replace(",", "."));

		} catch (NumberFormatException e) {

			tela.mostrarErro("Digite um salário válido.");
			return;
		}

		Funcionario funcionario = new Funcionario(proximoId++, nome, login, senha, funcao, salario);

		GeralController.getFuncionarios().add(funcionario);

		// Atualiza a tabela
		carregarFuncionarios(modeloTabela);

		tela.dispose();
	}

	// =========================================================
	// ALTERAR
	// =========================================================

	public static void alterarFuncionario(java.awt.Component parent, DefaultTableModel modeloTabela, int linha) {

		if (linha == -1) {

			JOptionPane.showMessageDialog(parent, "Selecione um funcionário para alterar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);

			return;
		}

		int id = (int) modeloTabela.getValueAt(linha, 0);

		Funcionario funcionario = buscarFuncionario(id);

		if (funcionario == null) {
			return;
		}

		String nome = JOptionPane.showInputDialog(parent, "Nome:", funcionario.getNome());

		if (nome == null || nome.trim().isEmpty()) {
			return;
		}

		String login = JOptionPane.showInputDialog(parent, "Login:", funcionario.getLogin());

		if (login == null || login.trim().isEmpty()) {
			return;
		}

		String senha = JOptionPane.showInputDialog(parent, "Senha:", funcionario.getSenha());

		if (senha == null || senha.trim().isEmpty()) {
			return;
		}

		String salarioTexto = JOptionPane.showInputDialog(parent, "Salário:", funcionario.getSalario());

		if (salarioTexto == null) {
			return;
		}

		double salario;

		try {

			salario = Double.parseDouble(salarioTexto.replace(",", "."));

		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(parent, "Digite um salário válido.", "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		funcionario.setNome(nome);
		funcionario.setLogin(login);
		funcionario.setSenha(senha);
		funcionario.setSalario(salario);

		carregarFuncionarios(modeloTabela);

		JOptionPane.showMessageDialog(parent, "Funcionário alterado com sucesso!");
	}

	// =========================================================
	// EXCLUIR
	// =========================================================

	public static void excluirFuncionario(java.awt.Component parent, DefaultTableModel modeloTabela, int linha) {

		if (linha == -1) {

			JOptionPane.showMessageDialog(parent, "Selecione um funcionário para excluir.", "Aviso",
					JOptionPane.WARNING_MESSAGE);

			return;
		}

		int id = (int) modeloTabela.getValueAt(linha, 0);

		Funcionario funcionario = buscarFuncionario(id);

		if (funcionario == null) {
			return;
		}

		int resposta = JOptionPane.showConfirmDialog(parent,
				"Tem certeza que deseja excluir o funcionário " + funcionario.getNome() + "?", "Excluir funcionário",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (resposta != JOptionPane.YES_OPTION) {
			return;
		}

		GeralController.getFuncionarios().remove(funcionario);

		carregarFuncionarios(modeloTabela);

		JOptionPane.showMessageDialog(parent, "Funcionário excluído com sucesso!");
	}

	// =========================================================
	// BUSCAR FUNCIONÁRIO
	// =========================================================

	private static Funcionario buscarFuncionario(int id) {

		for (Funcionario funcionario : GeralController.getFuncionarios()) {

			if (funcionario.getId() == id) {
				return funcionario;
			}
		}

		return null;
	}

	// =========================================================
	// CONVERTER FUNÇÃO
	// =========================================================

	private static String nomeFuncao(int funcao) {

		switch (funcao) {

		case 1:
			return "Atendente";

		case 2:
			return "Gerente";

		case 3:
			return "Administrador";

		default:
			return "Desconhecida";
		}
	}

	public static void IniciarTelaFuncionarios(TelaPrincipal tela, Funcionario funcionario) {
		if (funcionario.getFuncao() == 3) {
			TelaFuncionarios telafuncionario = new TelaFuncionarios(funcionario);
			telafuncionario.setVisible(true);
			tela.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Você não tem permissão para acessar!.", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}