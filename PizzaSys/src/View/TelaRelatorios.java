package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import Controller.FuncionariosController;
import Controller.PedidoController;
import Controller.RelatoriosController;
import Model.Funcionario;
import Model.Pedido;

public class TelaRelatorios extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblFaturamento;
	private JLabel lblPedidos;
	private JLabel lblDinheiro;
	private JLabel lblPix;
	private JLabel lblDebito;
	private JLabel lblCredito;
	private JLabel lblTotalFuncionarios;
	private JLabel lblTotalSalarios;
	private JLabel lblGerentes;
	private JLabel lblPizzaiolos;
	private JLabel lblAtendentes;
	private JLabel lblAdministradores;
	private JTable tabelaPedidos;
	private DefaultTableModel modeloTabelaPedidos;
	private JTable tabelaFuncionarios;
	private DefaultTableModel modeloTabelaFuncionarios;
	private final NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	private Funcionario funcionario;

	public TelaRelatorios(Funcionario funcionario) {
		this.funcionario = funcionario;
		if (!temPermissao()) {
			setLayout(new BorderLayout());
			JLabel acessoNegado = new JLabel("Acesso permitido somente para Gerentes e Administradores");
			acessoNegado.setFont(new Font("Tahoma", Font.BOLD, 18));
			acessoNegado.setHorizontalAlignment(SwingConstants.CENTER);
			add(acessoNegado, BorderLayout.CENTER);
			return;
		}
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JTabbedPane abas = new JTabbedPane();
		abas.addTab("Faturamento", criarPainelFaturamento());
		abas.addTab("Funcionários", criarPainelFuncionarios());
		add(abas, BorderLayout.CENTER);
		atualizarRelatorio();
	}

	private boolean temPermissao() {
		if (funcionario == null) {
			return false;
		}
		String funcao = FuncionariosController.nomeFuncao(funcionario.getFuncao());
		if (funcao == null) {
			return false;
		}
		funcao = funcao.trim().toLowerCase();
		return funcao.equals("gerente") || funcao.equals("administrador");
	}

	private JPanel criarPainelFaturamento() {
		JPanel painel = new JPanel(new BorderLayout(10, 10));
		JPanel painelTitulo = new JPanel(new BorderLayout());
		JLabel titulo = new JLabel("Relatório de Faturamento");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		painelTitulo.add(titulo, BorderLayout.WEST);
		painel.add(painelTitulo, BorderLayout.NORTH);
		JPanel painelResumo = new JPanel();
		painelResumo.setLayout(new javax.swing.BoxLayout(painelResumo, javax.swing.BoxLayout.Y_AXIS));
		painelResumo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lblFaturamento = criarLabelResumo("Faturamento total: R$ 0,00", 22);
		lblPedidos = criarLabelResumo("Pedidos concluídos: 0", 17);
		painelResumo.add(lblFaturamento);
		painelResumo.add(javax.swing.Box.createVerticalStrut(8));
		painelResumo.add(lblPedidos);
		painelResumo.add(javax.swing.Box.createVerticalStrut(20));
		JLabel tituloPagamentos = criarLabelResumo("Formas de pagamento", 18);
		painelResumo.add(tituloPagamentos);
		painelResumo.add(javax.swing.Box.createVerticalStrut(10));
		lblDinheiro = criarLabelResumo("Dinheiro: 0 pedidos - R$ 0,00", 15);
		lblPix = criarLabelResumo("Pix: 0 pedidos - R$ 0,00", 15);
		lblDebito = criarLabelResumo("Débito: 0 pedidos - R$ 0,00", 15);
		lblCredito = criarLabelResumo("Crédito: 0 pedidos - R$ 0,00", 15);
		painelResumo.add(lblDinheiro);
		painelResumo.add(javax.swing.Box.createVerticalStrut(8));
		painelResumo.add(lblPix);
		painelResumo.add(javax.swing.Box.createVerticalStrut(8));
		painelResumo.add(lblDebito);
		painelResumo.add(javax.swing.Box.createVerticalStrut(8));
		painelResumo.add(lblCredito);
		painel.add(painelResumo, BorderLayout.NORTH);
		painel.add(criarTabelaPedidos(), BorderLayout.CENTER);
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(e -> atualizarRelatorio());
		JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelBotao.add(btnAtualizar);
		painel.add(painelBotao, BorderLayout.SOUTH);
		return painel;
	}

	private JPanel criarTabelaPedidos() {
		JPanel painel = new JPanel(new BorderLayout());
		String[] colunas = { "Pedido", "Funcionário", "Data", "Forma de pagamento", "Valor" };
		modeloTabelaPedidos = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelaPedidos = new JTable(modeloTabelaPedidos);
		tabelaPedidos.setRowHeight(25);
		tabelaPedidos.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
		JScrollPane scroll = new JScrollPane(tabelaPedidos);
		painel.add(scroll, BorderLayout.CENTER);
		return painel;
	}

	private JPanel criarPainelFuncionarios() {
		JPanel painel = new JPanel(new BorderLayout(10, 10));
		JLabel titulo = new JLabel("Relatório de Funcionários");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		painel.add(titulo, BorderLayout.NORTH);
		JPanel painelResumo = new JPanel();
		painelResumo.setLayout(new javax.swing.BoxLayout(painelResumo, javax.swing.BoxLayout.Y_AXIS));
		painelResumo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lblTotalFuncionarios = criarLabelResumo("Total de funcionários: 0", 20);
		lblTotalSalarios = criarLabelResumo("Total gasto com salários: R$ 0,00", 20);
		lblGerentes = criarLabelResumo("Gerentes: 0", 16);
		lblPizzaiolos = criarLabelResumo("Pizzaiolos: 0", 16);
		lblAtendentes = criarLabelResumo("Atendentes: 0", 16);
		lblAdministradores = criarLabelResumo("Administradores: 0", 16);
		painelResumo.add(lblTotalFuncionarios);
		painelResumo.add(javax.swing.Box.createVerticalStrut(8));
		painelResumo.add(lblTotalSalarios);
		painelResumo.add(javax.swing.Box.createVerticalStrut(20));
		painelResumo.add(lblGerentes);
		painelResumo.add(javax.swing.Box.createVerticalStrut(8));
		painelResumo.add(lblPizzaiolos);
		painelResumo.add(javax.swing.Box.createVerticalStrut(8));
		painelResumo.add(lblAtendentes);
		painelResumo.add(javax.swing.Box.createVerticalStrut(8));
		painelResumo.add(lblAdministradores);
		painel.add(painelResumo, BorderLayout.NORTH);
		modeloTabelaFuncionarios = new DefaultTableModel(new String[] { "Funcionário", "Função", "Salário" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelaFuncionarios = new JTable(modeloTabelaFuncionarios);
		tabelaFuncionarios.setRowHeight(25);
		tabelaFuncionarios.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
		JScrollPane scroll = new JScrollPane(tabelaFuncionarios);
		painel.add(scroll, BorderLayout.CENTER);
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(e -> atualizarFuncionarios());
		JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelBotao.add(btnAtualizar);
		painel.add(painelBotao, BorderLayout.SOUTH);
		return painel;
	}

	private JLabel criarLabelResumo(String texto, int tamanho) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Tahoma", Font.BOLD, tamanho));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setAlignmentX(LEFT_ALIGNMENT);
		return label;
	}

	public void atualizarRelatorio() {
		if (!temPermissao()) {
			return;
		}
		double faturamento = RelatoriosController.getFaturamento();
		int pedidos = RelatoriosController.getQuantidadePedidos();
		lblFaturamento.setText("Faturamento total: " + moeda.format(faturamento));
		lblPedidos.setText("Pedidos concluídos: " + pedidos);
		lblDinheiro.setText("Dinheiro: " + RelatoriosController.getQuantidadeDinheiro() + " pedidos - "
				+ moeda.format(RelatoriosController.getFaturamentoDinheiro()));
		lblPix.setText("Pix: " + RelatoriosController.getQuantidadePix() + " pedidos - "
				+ moeda.format(RelatoriosController.getFaturamentoPix()));
		lblDebito.setText("Débito: " + RelatoriosController.getQuantidadeDebito() + " pedidos - "
				+ moeda.format(RelatoriosController.getFaturamentoDebito()));
		lblCredito.setText("Crédito: " + RelatoriosController.getQuantidadeCredito() + " pedidos - "
				+ moeda.format(RelatoriosController.getFaturamentoCredito()));
		atualizarTabelaPedidos();
		atualizarFuncionarios();
	}

	private void atualizarTabelaPedidos() {
		if (modeloTabelaPedidos == null) {
			return;
		}
		modeloTabelaPedidos.setRowCount(0);
		List<Pedido> pedidos = PedidoController.getPedidosConcluidos();
		for (Pedido pedido : pedidos) {
			String nomeFuncionario = pedido.getFuncionario() == null ? "Não informado"
					: pedido.getFuncionario().getNome();
			modeloTabelaPedidos.addRow(new Object[] { pedido.getId(), nomeFuncionario, pedido.getData(),
					RelatoriosController.nomeFormaPagamento(pedido.getFormaPagamento()),
					moeda.format(pedido.getValorTotal()) });
		}
	}

	private void atualizarFuncionarios() {
		if (modeloTabelaFuncionarios == null) {
			return;
		}
		modeloTabelaFuncionarios.setRowCount(0);
		List<Funcionario> funcionarios = FuncionariosController.getFuncionarios();
		int totalFuncionarios = funcionarios.size();
		int gerentes = 0;
		int pizzaiolos = 0;
		int atendentes = 0;
		int administradores = 0;
		double totalSalarios = 0;
		for (Funcionario funcionario : funcionarios) {
			totalSalarios += funcionario.getSalario();
			String funcao = FuncionariosController.nomeFuncao(funcionario.getFuncao());
			if (funcao == null) {
				funcao = "Não informado";
			}
			String funcaoNormalizada = funcao.trim().toLowerCase();
			if (funcaoNormalizada.equals("gerente")) {
				gerentes++;
			}
			if (funcaoNormalizada.equals("pizzaiolo")) {
				pizzaiolos++;
			}
			if (funcaoNormalizada.equals("atendente")) {
				atendentes++;
			}
			if (funcaoNormalizada.equals("administrador")) {
				administradores++;
			}
			modeloTabelaFuncionarios
					.addRow(new Object[] { funcionario.getNome(), funcao, moeda.format(funcionario.getSalario()) });
		}
		lblTotalFuncionarios.setText("Total de funcionários: " + totalFuncionarios);
		lblTotalSalarios.setText("Total gasto com salários: " + moeda.format(totalSalarios));
		lblGerentes.setText("Gerentes: " + gerentes);
		lblPizzaiolos.setText("Pizzaiolos: " + pizzaiolos);
		lblAtendentes.setText("Atendentes: " + atendentes);
		lblAdministradores.setText("Administradores: " + administradores);
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}
}