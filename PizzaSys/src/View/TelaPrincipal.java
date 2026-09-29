package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controller.GeralController;
import Controller.NotificacoesController;
import Model.Funcionario;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel panelMenu;
	private JPanel panelConteudo;
	private JPanel panelSuperior;
	private JPanel panelInferior;
	private JLabel lblUsuario;
	private JButton btnNotificacoes;
	private Funcionario funcionario;

	public TelaPrincipal(Funcionario funcionario) {
		this.funcionario = funcionario;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setTitle("PizzaSys");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));

		panelSuperior = new JPanel();
		panelSuperior.setLayout(new BorderLayout());
		contentPane.add(panelSuperior, BorderLayout.NORTH);

		JLabel lblTitulo = new JLabel("PizzaSys");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelSuperior.add(lblTitulo, BorderLayout.WEST);

		lblUsuario = new JLabel();

		if (funcionario != null) {
			lblUsuario.setText("Usuário: " + funcionario.getNome());
		}

		panelSuperior.add(lblUsuario, BorderLayout.EAST);

		panelMenu = new JPanel();

		if (funcionario != null && funcionario.getFuncao() == 3) {
			panelMenu.setLayout(new GridLayout(6, 1, 5, 5));
		} else {
			panelMenu.setLayout(new GridLayout(4, 1, 5, 5));
		}

		contentPane.add(panelMenu, BorderLayout.WEST);

		JButton btnPedidos = new JButton("Pedidos");

		btnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPedido();
			}
		});

		panelMenu.add(btnPedidos);

		if (funcionario != null && funcionario.getFuncao() == 3) {

			JButton btnFuncionarios = new JButton("Funcionários");

			btnFuncionarios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarFuncionarios();
				}
			});

			panelMenu.add(btnFuncionarios);
		}

		JButton btnPizzas = new JButton("Pizzas");

		btnPizzas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPizzas();
			}
		});

		panelMenu.add(btnPizzas);

		if (funcionario != null && funcionario.getFuncao() == 3) {

			JButton btnRelatorios = new JButton("Relatórios");

			btnRelatorios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarRelatorios();
				}
			});

			panelMenu.add(btnRelatorios);
		}

		btnNotificacoes = new JButton("Notificações (" + NotificacoesController.quantidadeNaoLidas(funcionario) + ")");
		btnNotificacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarNotificacoes();
			}
		});
		panelMenu.add(btnNotificacoes);

		JButton btnSair = new JButton("Sair");

		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeralController.sair();
			}
		});

		panelMenu.add(btnSair);

		panelConteudo = new JPanel();
		panelConteudo.setLayout(new BorderLayout(5, 5));
		contentPane.add(panelConteudo, BorderLayout.CENTER);

		mostrarInicio();

		panelInferior = new JPanel();
		contentPane.add(panelInferior, BorderLayout.SOUTH);

		JLabel lblStatus = new JLabel("PizzaSys - Sistema de gerenciamento");

		panelInferior.add(lblStatus);
	}

	public void mostrarInicio() {
		panelConteudo.removeAll();

		JLabel lblInicio;

		if (funcionario != null) {
			lblInicio = new JLabel("Olá " + funcionario.getNome());
		} else {
			lblInicio = new JLabel("Olá");
		}

		lblInicio.setHorizontalAlignment(SwingConstants.CENTER);

		lblInicio.setFont(new Font("Tahoma", Font.PLAIN, 28));

		panelConteudo.add(lblInicio, BorderLayout.CENTER);

		panelConteudo.revalidate();
		panelConteudo.repaint();
	}

	public void mostrarFuncionarios() {
		if (funcionario == null || funcionario.getFuncao() != 3) {
			GeralController.mostrarSemPermissao(this);
			return;
		}

		panelConteudo.removeAll();

		TelaFuncionarios telaFuncionarios = new TelaFuncionarios(funcionario);

		panelConteudo.add(telaFuncionarios, BorderLayout.CENTER);

		panelConteudo.revalidate();
		panelConteudo.repaint();
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void mostrarPedido() {
		panelConteudo.removeAll();

		TelaPedido telaPedido = new TelaPedido(funcionario);

		panelConteudo.add(telaPedido, BorderLayout.CENTER);

		panelConteudo.revalidate();
		panelConteudo.repaint();
	}

	public void mostrarPizzas() {
		panelConteudo.removeAll();

		TelaPizzas telaPizzas = new TelaPizzas();

		panelConteudo.add(telaPizzas, BorderLayout.CENTER);

		panelConteudo.revalidate();
		panelConteudo.repaint();
	}

	public void mostrarRelatorios() {
		if (funcionario == null || funcionario.getFuncao() != 3) {
			GeralController.mostrarSemPermissao(this);
			return;
		}

		panelConteudo.removeAll();

		TelaRelatorios telaRelatorios = new TelaRelatorios(funcionario);

		panelConteudo.add(telaRelatorios, BorderLayout.CENTER);

		panelConteudo.revalidate();
		panelConteudo.repaint();
	}

	public void mostrarNotificacoes() {
		panelConteudo.removeAll();
		TelaNotificacoes telaNotificacoes = new TelaNotificacoes(funcionario, this);
		panelConteudo.add(telaNotificacoes, BorderLayout.CENTER);
		atualizarContadorNotificacoes();
		panelConteudo.revalidate();
		panelConteudo.repaint();
	}

	public void atualizarContadorNotificacoes() {
		int quantidade = NotificacoesController.quantidadeNaoLidas(funcionario);
		btnNotificacoes.setText("Notificações (" + quantidade + ")");
	}
}