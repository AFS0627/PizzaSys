package View;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controller.NotificacoesController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaSenha extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textFieldNome;
	private JTextField textFieldLogin;
	private JTextArea textAreaMensagem;
	private JButton btnEnviar;

	public TelaSenha() {

		setTitle("Recuperação de Senha - PizzaSys");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 350);

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

		JLabel lblNome = new JLabel("Nome:");

		GridBagConstraints gbc_lblNome = new GridBagConstraints();

		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(5, 5, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;

		contentPane.add(lblNome, gbc_lblNome);

		textFieldNome = new JTextField();

		GridBagConstraints gbc_textFieldNome = new GridBagConstraints();

		gbc_textFieldNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNome.weightx = 1.0;
		gbc_textFieldNome.insets = new Insets(5, 5, 5, 0);
		gbc_textFieldNome.gridx = 1;
		gbc_textFieldNome.gridy = 1;

		contentPane.add(textFieldNome, gbc_textFieldNome);

		textFieldNome.setColumns(10);

		JLabel lblLogin = new JLabel("Login:");

		GridBagConstraints gbc_lblLogin = new GridBagConstraints();

		gbc_lblLogin.anchor = GridBagConstraints.EAST;
		gbc_lblLogin.insets = new Insets(5, 5, 5, 5);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 2;

		contentPane.add(lblLogin, gbc_lblLogin);

		textFieldLogin = new JTextField();

		GridBagConstraints gbc_textFieldLogin = new GridBagConstraints();

		gbc_textFieldLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLogin.weightx = 1.0;
		gbc_textFieldLogin.insets = new Insets(5, 5, 5, 0);
		gbc_textFieldLogin.gridx = 1;
		gbc_textFieldLogin.gridy = 2;

		contentPane.add(textFieldLogin, gbc_textFieldLogin);

		textFieldLogin.setColumns(10);

		JLabel lblMensagem = new JLabel("Mensagem:");

		GridBagConstraints gbc_lblMensagem = new GridBagConstraints();

		gbc_lblMensagem.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblMensagem.insets = new Insets(5, 5, 5, 5);
		gbc_lblMensagem.gridx = 0;
		gbc_lblMensagem.gridy = 3;

		contentPane.add(lblMensagem, gbc_lblMensagem);

		textAreaMensagem = new JTextArea();

		textAreaMensagem.setLineWrap(true);
		textAreaMensagem.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(textAreaMensagem);

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();

		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.weighty = 1.0;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;

		contentPane.add(scrollPane, gbc_scrollPane);

		btnEnviar = new JButton("Enviar");
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				enviarSolicitacao();
			}
		});
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();

		gbc_btnEnviar.gridwidth = 2;
		gbc_btnEnviar.insets = new Insets(20, 5, 5, 0);
		gbc_btnEnviar.gridx = 0;
		gbc_btnEnviar.gridy = 4;

		contentPane.add(btnEnviar, gbc_btnEnviar);
	}

	private void enviarSolicitacao() {

		String nome = textFieldNome.getText().trim();
		String login = textFieldLogin.getText().trim();
		String mensagem = textAreaMensagem.getText().trim();

		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Digite seu nome.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (login.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Digite seu login.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (mensagem.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Digite uma mensagem.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String titulo = "Solicitação de recuperação de senha";

		String texto = "Funcionário: " + nome + "\nLogin: " + login + "\n\nMensagem:\n" + mensagem;

		try {

			NotificacoesController.enviarParaAdministradores(titulo, texto);

			JOptionPane.showMessageDialog(this, "Sua solicitação foi enviada aos administradores.", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);

			textFieldNome.setText("");
			textFieldLogin.setText("");
			textAreaMensagem.setText("");

		} catch (RuntimeException e) {

			JOptionPane.showMessageDialog(this, "Não foi possível enviar a solicitação.", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}