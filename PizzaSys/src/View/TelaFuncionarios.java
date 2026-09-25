package View;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Controller.FuncionariosController;
import Model.Funcionario;

public class TelaFuncionarios extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable tabelaFuncionarios;
	private DefaultTableModel modeloTabela;

	private Funcionario funcionario;

	public TelaFuncionarios(Funcionario funcionario) {

		this.funcionario = funcionario;

		setLayout(new BorderLayout(5, 5));

		JLabel lblFuncionarios = new JLabel("Funcionários");
		lblFuncionarios.setFont(new Font("Tahoma", Font.BOLD, 18));

		add(lblFuncionarios, BorderLayout.NORTH);

		modeloTabela = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Nome", "Login", "Função", "Salário" }) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabelaFuncionarios = new JTable(modeloTabela);

		tabelaFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(tabelaFuncionarios);

		add(scrollPane, BorderLayout.CENTER);

		JPanel panelBotoes = new JPanel();

		JButton btnIncluir = new JButton("Incluir");

		btnIncluir.addActionListener(e -> incluirFuncionario());

		panelBotoes.add(btnIncluir);

		JButton btnAlterar = new JButton("Alterar");

		btnAlterar.addActionListener(e -> alterarFuncionario());

		panelBotoes.add(btnAlterar);

		JButton btnExcluir = new JButton("Excluir");

		btnExcluir.addActionListener(e -> excluirFuncionario());

		panelBotoes.add(btnExcluir);

		add(panelBotoes, BorderLayout.SOUTH);

		atualizarTabela();
	}

	private void incluirFuncionario() {

		TelaIncluirFuncionario tela = new TelaIncluirFuncionario(this);

		tela.setVisible(true);
	}

	public void atualizarTabela() {

		modeloTabela.setRowCount(0);

		for (Funcionario funcionario : FuncionariosController.getFuncionarios()) {

			modeloTabela.addRow(new Object[] { funcionario.getId(), funcionario.getNome(), funcionario.getLogin(),
					FuncionariosController.nomeFuncao(funcionario.getFuncao()),
					String.format("R$ %.2f", funcionario.getSalario()) });
		}
	}

	private void alterarFuncionario() {

		int linha = tabelaFuncionarios.getSelectedRow();

		if (linha == -1) {

			JOptionPane.showMessageDialog(this, "Selecione um funcionário para alterar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);

			return;
		}

		int id = (int) modeloTabela.getValueAt(linha, 0);

		Funcionario funcionario = FuncionariosController.buscarFuncionario(id);

		if (funcionario == null) {

			JOptionPane.showMessageDialog(this, "Funcionário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		String nome = JOptionPane.showInputDialog(this, "Nome:", funcionario.getNome());

		if (nome == null) {
			return;
		}

		String login = JOptionPane.showInputDialog(this, "Login:", funcionario.getLogin());

		if (login == null) {
			return;
		}

		String senha = JOptionPane.showInputDialog(this, "Senha:", funcionario.getSenha());

		if (senha == null) {
			return;
		}

		String salario = JOptionPane.showInputDialog(this, "Salário:", funcionario.getSalario());

		if (salario == null) {
			return;
		}

		String erro = FuncionariosController.alterarFuncionario(id, nome, login, senha, salario);

		if (erro != null) {

			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		atualizarTabela();

		JOptionPane.showMessageDialog(this, "Funcionário alterado com sucesso!");
	}

	private void excluirFuncionario() {

		int linha = tabelaFuncionarios.getSelectedRow();

		if (linha == -1) {

			JOptionPane.showMessageDialog(this, "Selecione um funcionário para excluir.", "Aviso",
					JOptionPane.WARNING_MESSAGE);

			return;
		}

		int id = (int) modeloTabela.getValueAt(linha, 0);

		Funcionario funcionario = FuncionariosController.buscarFuncionario(id);

		if (funcionario == null) {

			JOptionPane.showMessageDialog(this, "Funcionário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		int resposta = JOptionPane.showConfirmDialog(this,
				"Tem certeza que deseja excluir o funcionário " + funcionario.getNome() + "?", "Excluir funcionário",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (resposta != JOptionPane.YES_OPTION) {
			return;
		}

		boolean excluido = FuncionariosController.excluirFuncionario(id);

		if (!excluido) {

			JOptionPane.showMessageDialog(this, "Não foi possível excluir o funcionário.", "Erro",
					JOptionPane.ERROR_MESSAGE);

			return;
		}

		atualizarTabela();

		JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}
}