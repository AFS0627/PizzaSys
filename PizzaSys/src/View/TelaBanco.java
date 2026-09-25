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

public class TelaBanco extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldServidor;
	private JPasswordField passwordFieldSenha;
	private JButton btnNewButtonSalvar;
	private JLabel lblNewLabelTeste;
	private JLabel lblUsuario;
	private JTextField textFieldUsuario;
	private JLabel lblBanco;
	private JTextField textFieldBanco;

	public TelaBanco() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 311, 346);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0 };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblPizzaSys = new JLabel("PizzaSys");
		GridBagConstraints gbc_lblPizzaSys = new GridBagConstraints();
		gbc_lblPizzaSys.gridwidth = 2;
		gbc_lblPizzaSys.insets = new Insets(0, 0, 20, 0);
		gbc_lblPizzaSys.gridx = 0;
		gbc_lblPizzaSys.gridy = 0;
		contentPane.add(lblPizzaSys, gbc_lblPizzaSys);

		JLabel lblServidor = new JLabel("Servidor:");
		GridBagConstraints gbc_lblServidor = new GridBagConstraints();
		gbc_lblServidor.anchor = GridBagConstraints.EAST;
		gbc_lblServidor.insets = new Insets(5, 5, 5, 5);
		gbc_lblServidor.gridx = 0;
		gbc_lblServidor.gridy = 1;
		contentPane.add(lblServidor, gbc_lblServidor);

		textFieldServidor = new JTextField();
		GridBagConstraints gbc_textFieldServidor = new GridBagConstraints();
		gbc_textFieldServidor.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldServidor.weightx = 1.0;
		gbc_textFieldServidor.insets = new Insets(5, 5, 5, 0);
		gbc_textFieldServidor.gridx = 1;
		gbc_textFieldServidor.gridy = 1;
		contentPane.add(textFieldServidor, gbc_textFieldServidor);
		textFieldServidor.setColumns(10);

		lblUsuario = new JLabel("Usuario:");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 0;
		gbc_lblUsuario.gridy = 2;
		contentPane.add(lblUsuario, gbc_lblUsuario);

		textFieldUsuario = new JTextField();
		GridBagConstraints gbc_textFieldUsuario = new GridBagConstraints();
		gbc_textFieldUsuario.insets = new Insets(5, 5, 5, 0);
		gbc_textFieldUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUsuario.gridx = 1;
		gbc_textFieldUsuario.gridy = 2;
		contentPane.add(textFieldUsuario, gbc_textFieldUsuario);
		textFieldUsuario.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		GridBagConstraints gbc_lblSenha = new GridBagConstraints();
		gbc_lblSenha.anchor = GridBagConstraints.EAST;
		gbc_lblSenha.insets = new Insets(5, 5, 5, 5);
		gbc_lblSenha.gridx = 0;
		gbc_lblSenha.gridy = 3;
		contentPane.add(lblSenha, gbc_lblSenha);

		btnNewButtonSalvar = new JButton("Salvar");
		btnNewButtonSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Teste");
			}
		});

		passwordFieldSenha = new JPasswordField();
		GridBagConstraints gbc_passwordFieldSenha = new GridBagConstraints();
		gbc_passwordFieldSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldSenha.weightx = 1.0;
		gbc_passwordFieldSenha.insets = new Insets(5, 5, 5, 0);
		gbc_passwordFieldSenha.gridx = 1;
		gbc_passwordFieldSenha.gridy = 3;
		contentPane.add(passwordFieldSenha, gbc_passwordFieldSenha);

		lblBanco = new JLabel("Banco:");
		GridBagConstraints gbc_lblBanco = new GridBagConstraints();
		gbc_lblBanco.anchor = GridBagConstraints.EAST;
		gbc_lblBanco.insets = new Insets(0, 0, 5, 5);
		gbc_lblBanco.gridx = 0;
		gbc_lblBanco.gridy = 4;
		contentPane.add(lblBanco, gbc_lblBanco);

		textFieldBanco = new JTextField();
		GridBagConstraints gbc_textFieldBanco = new GridBagConstraints();
		gbc_textFieldBanco.weightx = 1.0;
		gbc_textFieldBanco.insets = new Insets(5, 5, 5, 0);
		gbc_textFieldBanco.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldBanco.gridx = 1;
		gbc_textFieldBanco.gridy = 4;
		contentPane.add(textFieldBanco, gbc_textFieldBanco);
		textFieldBanco.setColumns(10);

		GridBagConstraints gbc_btnNewButtonSalvar = new GridBagConstraints();
		gbc_btnNewButtonSalvar.gridwidth = 2;
		gbc_btnNewButtonSalvar.insets = new Insets(20, 5, 5, 0);
		gbc_btnNewButtonSalvar.gridx = 0;
		gbc_btnNewButtonSalvar.gridy = 5;
		contentPane.add(btnNewButtonSalvar, gbc_btnNewButtonSalvar);

		lblNewLabelTeste = new JLabel("Teste");
		lblNewLabelTeste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		lblNewLabelTeste.setForeground(SystemColor.textHighlight);
		GridBagConstraints gbc_lblNewLabelTeste = new GridBagConstraints();
		gbc_lblNewLabelTeste.gridwidth = 2;
		gbc_lblNewLabelTeste.insets = new Insets(5, 5, 5, 0);
		gbc_lblNewLabelTeste.gridx = 0;
		gbc_lblNewLabelTeste.gridy = 6;
		contentPane.add(lblNewLabelTeste, gbc_lblNewLabelTeste);
	}
}