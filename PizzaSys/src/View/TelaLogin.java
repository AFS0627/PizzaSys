package View;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controller.LoginController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JPasswordField passwordFieldSenha;
	private JButton btnEntrar;
	private JLabel lblEsqueciSenha;
	private JLabel lblCredenciais;


	public TelaLogin() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 311, 346);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblPizzaSys = new JLabel("PizzaSys");
		GridBagConstraints gbc_lblPizzaSys = new GridBagConstraints();
		gbc_lblPizzaSys.gridwidth = 2;
		gbc_lblPizzaSys.insets = new Insets(0, 0, 20, 0);
		gbc_lblPizzaSys.gridx = 0;
		gbc_lblPizzaSys.gridy = 0;
		contentPane.add(lblPizzaSys, gbc_lblPizzaSys);

		JLabel lblLogin = new JLabel("Login:");
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.anchor = GridBagConstraints.EAST;
		gbc_lblLogin.insets = new Insets(5, 5, 5, 5);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 1;
		contentPane.add(lblLogin, gbc_lblLogin);

		textFieldLogin = new JTextField();
		GridBagConstraints gbc_textFieldLogin = new GridBagConstraints();
		gbc_textFieldLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLogin.weightx = 1.0;
		gbc_textFieldLogin.insets = new Insets(5, 5, 5, 0);
		gbc_textFieldLogin.gridx = 1;
		gbc_textFieldLogin.gridy = 1;
		contentPane.add(textFieldLogin, gbc_textFieldLogin);
		textFieldLogin.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		GridBagConstraints gbc_lblSenha = new GridBagConstraints();
		gbc_lblSenha.anchor = GridBagConstraints.EAST;
		gbc_lblSenha.insets = new Insets(5, 5, 5, 5);
		gbc_lblSenha.gridx = 0;
		gbc_lblSenha.gridy = 2;
		contentPane.add(lblSenha, gbc_lblSenha);

		passwordFieldSenha = new JPasswordField();
		GridBagConstraints gbc_passwordFieldSenha = new GridBagConstraints();
		gbc_passwordFieldSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldSenha.weightx = 1.0;
		gbc_passwordFieldSenha.insets = new Insets(5, 5, 5, 0);
		gbc_passwordFieldSenha.gridx = 1;
		gbc_passwordFieldSenha.gridy = 2;
		contentPane.add(passwordFieldSenha, gbc_passwordFieldSenha);

		btnEntrar = new JButton("Entrar");
		btnEntrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Teste");
			}
		});
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String login = textFieldLogin.getText();
				String senha = new String(passwordFieldSenha.getPassword());

				LoginController.realizarLogin(login, senha, TelaLogin.this);
			}
		});
		getRootPane().setDefaultButton(btnEntrar);
		GridBagConstraints gbc_btnEntrar = new GridBagConstraints();
		gbc_btnEntrar.gridwidth = 2;
		gbc_btnEntrar.insets = new Insets(20, 5, 5, 0);
		gbc_btnEntrar.gridx = 0;
		gbc_btnEntrar.gridy = 3;
		contentPane.add(btnEntrar, gbc_btnEntrar);
		
		lblCredenciais = new JLabel("Credenciais do banco");
		lblCredenciais.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginController.IniciarTelaBanco();
			}
		});
		lblCredenciais.setForeground(SystemColor.textHighlight);
		GridBagConstraints gbc_lblCredenciais = new GridBagConstraints();
		gbc_lblCredenciais.gridwidth = 2;
		gbc_lblCredenciais.insets = new Insets(5, 5, 0, 0);
		gbc_lblCredenciais.gridx = 0;
		gbc_lblCredenciais.gridy = 4;
		contentPane.add(lblCredenciais, gbc_lblCredenciais);

		lblEsqueciSenha = new JLabel("Esqueci a senha");
		lblEsqueciSenha.setForeground(SystemColor.textHighlight);

		GridBagConstraints gbc_lblEsqueciSenha = new GridBagConstraints();
		gbc_lblEsqueciSenha.gridwidth = 2;
		gbc_lblEsqueciSenha.insets = new Insets(5, 5, 0, 0);
		gbc_lblEsqueciSenha.gridx = 0;
		gbc_lblEsqueciSenha.gridy = 5;
		contentPane.add(lblEsqueciSenha, gbc_lblEsqueciSenha);
	}
}