package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.GeralController;
import Model.Funcionario;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel panelMenu;
	private JPanel panelConteudo;
	private JPanel panelSuperior;
	private JPanel panelInferior;

	private JLabel lblUsuario;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaPrincipal(Funcionario funcionario) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setTitle("PizzaSys");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));

		panelSuperior = new JPanel();
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout());

		JLabel lblTitulo = new JLabel("PizzaSys");
		panelSuperior.add(lblTitulo, BorderLayout.WEST);

		lblUsuario = new JLabel();
		panelSuperior.add(lblUsuario, BorderLayout.EAST);

		if (funcionario != null) {
			lblUsuario.setText("Usuário: " + funcionario.getNome());
		}

		panelMenu = new JPanel();
		contentPane.add(panelMenu, BorderLayout.WEST);
		panelMenu.setLayout(new GridLayout(6, 1, 5, 5));

		JButton btnPedidos = new JButton("Pedidos");
		panelMenu.add(btnPedidos);

		JButton btnFuncionarios = new JButton("Funcionários");
		btnFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeralController.IniciarTelaFuncionarios(TelaPrincipal.this, funcionario);
			}
		});
		panelMenu.add(btnFuncionarios);

		JButton btnPizzas = new JButton("Pizzas");
		panelMenu.add(btnPizzas);

		JButton btnRelatorios = new JButton("Relatórios");
		panelMenu.add(btnRelatorios);

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeralController.sair();
			}
		});

		JButton btnNotificacoes = new JButton("Notificações(" + GeralController.QntdNotificacao(funcionario) + ")");
		panelMenu.add(btnNotificacoes);
		panelMenu.add(btnSair);

		panelConteudo = new JPanel();
		contentPane.add(panelConteudo, BorderLayout.CENTER);
		panelConteudo.setLayout(new BorderLayout());

		JLabel lblInicio = new JLabel("Olá " + funcionario.getNome());
		lblInicio.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicio.setFont(new Font("Tahoma", Font.PLAIN, 28));
		panelConteudo.add(lblInicio, BorderLayout.CENTER);

		panelInferior = new JPanel();
		contentPane.add(panelInferior, BorderLayout.SOUTH);

		JLabel lblStatus = new JLabel("PizzaSys - Sistema de gerenciamento");
		panelInferior.add(lblStatus);
	}
}