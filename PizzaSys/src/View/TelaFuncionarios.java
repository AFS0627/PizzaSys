package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controller.GeralController;
import Model.Funcionario;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaFuncionarios extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel panelMenu;
	private JPanel panelConteudo;
	private JPanel panelSuperior;
	private JPanel panelInferior;

	private JLabel lblUsuario;

	private JTable tabelaFuncionarios;
	private DefaultTableModel modeloTabela;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaFuncionarios frame = new TelaFuncionarios(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaFuncionarios(Funcionario funcionario) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setTitle("PizzaSys");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));

		// =========================
		// PAINEL SUPERIOR
		// =========================

		panelSuperior = new JPanel();
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout());

		JLabel lblTitulo = new JLabel("PizzaSys");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelSuperior.add(lblTitulo, BorderLayout.WEST);

		lblUsuario = new JLabel();

		if (funcionario != null) {
			lblUsuario.setText("Usuário: " + funcionario.getNome());
		}

		panelSuperior.add(lblUsuario, BorderLayout.EAST);

		// =========================
		// MENU
		// =========================

		panelMenu = new JPanel();
		contentPane.add(panelMenu, BorderLayout.WEST);
		panelMenu.setLayout(new GridLayout(6, 1, 5, 5));

		JButton btnPedidos = new JButton("Pedidos");
		panelMenu.add(btnPedidos);

		JButton btnFuncionarios = new JButton("Funcionários");
		btnFuncionarios.setBackground(Color.RED);
		panelMenu.add(btnFuncionarios);

		JButton btnPizzas = new JButton("Pizzas");
		panelMenu.add(btnPizzas);

		JButton btnRelatorios = new JButton("Relatórios");
		panelMenu.add(btnRelatorios);

		JButton btnNotificacoes = new JButton("Notificações (" + GeralController.QntdNotificacao(funcionario) + ")");

		panelMenu.add(btnNotificacoes);

		JButton btnSair = new JButton("Sair");

		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeralController.sair();
			}
		});

		panelMenu.add(btnSair);

		// =========================
		// CONTEÚDO
		// =========================

		panelConteudo = new JPanel();
		contentPane.add(panelConteudo, BorderLayout.CENTER);
		panelConteudo.setLayout(new BorderLayout(5, 5));

		JLabel lblFuncionarios = new JLabel("Funcionários");
		lblFuncionarios.setFont(new Font("Tahoma", Font.BOLD, 18));

		panelConteudo.add(lblFuncionarios, BorderLayout.NORTH);

		// =========================
		// TABELA
		// =========================

		modeloTabela = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Nome", "Login", "Função", "Salário" }) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabelaFuncionarios = new JTable(modeloTabela);

		tabelaFuncionarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(tabelaFuncionarios);

		panelConteudo.add(scrollPane, BorderLayout.CENTER);

		// =========================
		// BOTÕES
		// =========================

		JPanel panelBotoes = new JPanel();

		JButton btnIncluir = new JButton("Incluir");

		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeralController.IniciarTelaIncluirFuncionario(modeloTabela);
			}
		});

		panelBotoes.add(btnIncluir);

		JButton btnAlterar = new JButton("Alterar");

		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int linha = tabelaFuncionarios.getSelectedRow();

				GeralController.alterarFuncionario(TelaFuncionarios.this, modeloTabela, linha);
			}
		});

		panelBotoes.add(btnAlterar);

		JButton btnExcluir = new JButton("Excluir");

		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int linha = tabelaFuncionarios.getSelectedRow();

				GeralController.excluirFuncionario(TelaFuncionarios.this, modeloTabela, linha);
			}
		});

		panelBotoes.add(btnExcluir);

		panelConteudo.add(panelBotoes, BorderLayout.SOUTH);

		// Carrega os funcionários na abertura
		GeralController.carregarFuncionarios(modeloTabela);

		// =========================
		// RODAPÉ
		// =========================

		panelInferior = new JPanel();
		contentPane.add(panelInferior, BorderLayout.SOUTH);

		JLabel lblStatus = new JLabel("PizzaSys - Sistema de gerenciamento");

		panelInferior.add(lblStatus);
	}
}