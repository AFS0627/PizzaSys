package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
		JTextField txtNome = new JTextField();
		JTextField txtLogin = new JTextField();
		JPasswordField txtSenha = new JPasswordField();
		JComboBox<String> cbFuncao = new JComboBox<>();
		cbFuncao.addItem("Atendente");
		cbFuncao.addItem("Gerente");
		cbFuncao.addItem("Administrador");
		JTextField txtSalario = new JTextField();
		JPanel painel = new JPanel(new GridLayout(5, 2, 5, 5));
		painel.add(new JLabel("Nome:"));
		painel.add(txtNome);
		painel.add(new JLabel("Login:"));
		painel.add(txtLogin);
		painel.add(new JLabel("Senha:"));
		painel.add(txtSenha);
		painel.add(new JLabel("Função:"));
		painel.add(cbFuncao);
		painel.add(new JLabel("Salário:"));
		painel.add(txtSalario);
		int resposta = JOptionPane.showConfirmDialog(this, painel, "Incluir Funcionário", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (resposta != JOptionPane.OK_OPTION) {
			return;
		}
		String erro = FuncionariosController.cadastrarFuncionario(txtNome.getText(), txtLogin.getText(),
				new String(txtSenha.getPassword()), cbFuncao.getSelectedIndex() + 1, txtSalario.getText());
		if (erro != null) {
			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
		atualizarTabela();
		JOptionPane.showMessageDialog(this, "Funcionário incluído com sucesso!");
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
		JTextField txtNome = new JTextField(funcionario.getNome());
		JTextField txtLogin = new JTextField(funcionario.getLogin());
		JPasswordField txtSenha = new JPasswordField(funcionario.getSenha());
		JComboBox<String> cbFuncao = new JComboBox<>();
		cbFuncao.addItem("Atendente");
		cbFuncao.addItem("Gerente");
		cbFuncao.addItem("Administrador");
		if (funcionario.getFuncao() >= 1 && funcionario.getFuncao() <= 3) {
			cbFuncao.setSelectedIndex(funcionario.getFuncao() - 1);
		}
		JTextField txtSalario = new JTextField(String.valueOf(funcionario.getSalario()));
		JPanel painel = new JPanel(new GridLayout(5, 2, 5, 5));
		painel.add(new JLabel("Nome:"));
		painel.add(txtNome);
		painel.add(new JLabel("Login:"));
		painel.add(txtLogin);
		painel.add(new JLabel("Senha:"));
		painel.add(txtSenha);
		painel.add(new JLabel("Função:"));
		painel.add(cbFuncao);
		painel.add(new JLabel("Salário:"));
		painel.add(txtSalario);
		int resposta = JOptionPane.showConfirmDialog(this, painel, "Editar Funcionário", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (resposta != JOptionPane.OK_OPTION) {
			return;
		}
		String erro = FuncionariosController.alterarFuncionario(id, txtNome.getText(), txtLogin.getText(),
				new String(txtSenha.getPassword()), cbFuncao.getSelectedIndex() + 1, txtSalario.getText());
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

	public void atualizarTabela() {
		modeloTabela.setRowCount(0);
		for (Funcionario funcionario : FuncionariosController.getFuncionarios()) {
			modeloTabela.addRow(new Object[] { funcionario.getId(), funcionario.getNome(), funcionario.getLogin(),
					FuncionariosController.nomeFuncao(funcionario.getFuncao()),
					String.format("R$ %.2f", funcionario.getSalario()) });
		}
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}
}