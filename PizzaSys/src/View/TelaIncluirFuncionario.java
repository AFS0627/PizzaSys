package View;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controller.GeralController;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaIncluirFuncionario extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private DefaultTableModel modeloTabela;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JTextField txtSalario;

	private JComboBox<String> cbFuncao;

	private JLabel lblErro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaIncluirFuncionario frame = new TelaIncluirFuncionario(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaIncluirFuncionario(DefaultTableModel modeloTabela) {

		this.modeloTabela = modeloTabela;
		setTitle("PizzaSys - Incluir Funcionário");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);

		contentPane.setLayout(new GridBagLayout());

		// =========================
		// TÍTULO
		// =========================

		JLabel lblTitulo = new JLabel("Incluir Funcionário");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));

		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.gridwidth = 2;
		gbc_lblTitulo.insets = new Insets(5, 5, 15, 5);
		gbc_lblTitulo.gridx = 0;
		gbc_lblTitulo.gridy = 0;

		contentPane.add(lblTitulo, gbc_lblTitulo);

		// =========================
		// NOME
		// =========================

		JLabel lblNome = new JLabel("Nome:");

		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(5, 5, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;

		contentPane.add(lblNome, gbc_lblNome);

		txtNome = new JTextField();

		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.weightx = 1.0;
		gbc_txtNome.insets = new Insets(5, 5, 5, 5);
		gbc_txtNome.gridx = 1;
		gbc_txtNome.gridy = 1;

		contentPane.add(txtNome, gbc_txtNome);
		txtNome.setColumns(20);

		// =========================
		// LOGIN
		// =========================

		JLabel lblLogin = new JLabel("Login:");

		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.anchor = GridBagConstraints.EAST;
		gbc_lblLogin.insets = new Insets(5, 5, 5, 5);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 2;

		contentPane.add(lblLogin, gbc_lblLogin);

		txtLogin = new JTextField();

		GridBagConstraints gbc_txtLogin = new GridBagConstraints();
		gbc_txtLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLogin.weightx = 1.0;
		gbc_txtLogin.insets = new Insets(5, 5, 5, 5);
		gbc_txtLogin.gridx = 1;
		gbc_txtLogin.gridy = 2;

		contentPane.add(txtLogin, gbc_txtLogin);

		// =========================
		// SENHA
		// =========================

		JLabel lblSenha = new JLabel("Senha:");

		GridBagConstraints gbc_lblSenha = new GridBagConstraints();
		gbc_lblSenha.anchor = GridBagConstraints.EAST;
		gbc_lblSenha.insets = new Insets(5, 5, 5, 5);
		gbc_lblSenha.gridx = 0;
		gbc_lblSenha.gridy = 3;

		contentPane.add(lblSenha, gbc_lblSenha);

		txtSenha = new JPasswordField();

		GridBagConstraints gbc_txtSenha = new GridBagConstraints();
		gbc_txtSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSenha.weightx = 1.0;
		gbc_txtSenha.insets = new Insets(5, 5, 5, 5);
		gbc_txtSenha.gridx = 1;
		gbc_txtSenha.gridy = 3;

		contentPane.add(txtSenha, gbc_txtSenha);

		// =========================
		// FUNÇÃO
		// =========================

		JLabel lblFuncao = new JLabel("Função:");

		GridBagConstraints gbc_lblFuncao = new GridBagConstraints();
		gbc_lblFuncao.anchor = GridBagConstraints.EAST;
		gbc_lblFuncao.insets = new Insets(5, 5, 5, 5);
		gbc_lblFuncao.gridx = 0;
		gbc_lblFuncao.gridy = 4;

		contentPane.add(lblFuncao, gbc_lblFuncao);

		cbFuncao = new JComboBox<String>();

		cbFuncao.addItem("Atendente");
		cbFuncao.addItem("Gerente");
		cbFuncao.addItem("Administrador");

		GridBagConstraints gbc_cbFuncao = new GridBagConstraints();
		gbc_cbFuncao.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbFuncao.weightx = 1.0;
		gbc_cbFuncao.insets = new Insets(5, 5, 5, 5);
		gbc_cbFuncao.gridx = 1;
		gbc_cbFuncao.gridy = 4;

		contentPane.add(cbFuncao, gbc_cbFuncao);

		// =========================
		// SALÁRIO
		// =========================

		JLabel lblSalario = new JLabel("Salário:");

		GridBagConstraints gbc_lblSalario = new GridBagConstraints();
		gbc_lblSalario.anchor = GridBagConstraints.EAST;
		gbc_lblSalario.insets = new Insets(5, 5, 5, 5);
		gbc_lblSalario.gridx = 0;
		gbc_lblSalario.gridy = 5;

		contentPane.add(lblSalario, gbc_lblSalario);

		txtSalario = new JTextField();

		GridBagConstraints gbc_txtSalario = new GridBagConstraints();
		gbc_txtSalario.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSalario.weightx = 1.0;
		gbc_txtSalario.insets = new Insets(5, 5, 5, 5);
		gbc_txtSalario.gridx = 1;
		gbc_txtSalario.gridy = 5;

		contentPane.add(txtSalario, gbc_txtSalario);

		// =========================
		// MENSAGEM DE ERRO
		// =========================

		lblErro = new JLabel(" ");

		lblErro.setForeground(Color.RED);

		GridBagConstraints gbc_lblErro = new GridBagConstraints();
		gbc_lblErro.gridwidth = 2;
		gbc_lblErro.insets = new Insets(5, 5, 5, 5);
		gbc_lblErro.gridx = 0;
		gbc_lblErro.gridy = 6;

		contentPane.add(lblErro, gbc_lblErro);

		// =========================
		// BOTÃO CANCELAR
		// =========================

		JButton btnCancelar = new JButton("Cancelar");

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.EAST;
		gbc_btnCancelar.insets = new Insets(10, 5, 5, 5);
		gbc_btnCancelar.gridx = 0;
		gbc_btnCancelar.gridy = 7;

		contentPane.add(btnCancelar, gbc_btnCancelar);

		// =========================
		// BOTÃO SALVAR
		// =========================

		JButton btnSalvar = new JButton("Salvar");

		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GeralController.cadastrarFuncionario(TelaIncluirFuncionario.this, modeloTabela, txtNome.getText(),
						txtLogin.getText(), new String(txtSenha.getPassword()), cbFuncao.getSelectedIndex() + 1,
						txtSalario.getText());
			}
		});

		GridBagConstraints gbc_btnSalvar = new GridBagConstraints();
		gbc_btnSalvar.anchor = GridBagConstraints.WEST;
		gbc_btnSalvar.insets = new Insets(10, 5, 5, 5);
		gbc_btnSalvar.gridx = 1;
		gbc_btnSalvar.gridy = 7;

		contentPane.add(btnSalvar, gbc_btnSalvar);
	}

	/**
	 * Exibe uma mensagem de erro na própria tela.
	 */
	public void mostrarErro(String mensagem) {
		lblErro.setText(mensagem);
	}
}