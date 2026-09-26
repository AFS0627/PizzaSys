package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Controller.PedidoController;
import Controller.PizzaController;
import Model.Funcionario;
import Model.ItemPedido;
import Model.Pedido;
import Model.Pizza;

public class TelaPedido extends JPanel {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;
	private Pedido pedido;

	private JPanel panelPizzas;
	private JPanel panelItens;
	private JPanel panelPedidos;

	private JLabel lblTotal;
	private JLabel lblStatus;
	private JLabel lblTitulo;

	private JTabbedPane abas;

	private final Dimension TAMANHO_BOTAO = new Dimension(90, 28);

	public TelaPedido(Funcionario funcionario) {
		this.funcionario = funcionario;

		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		abas = new JTabbedPane();

		abas.addTab("Fazer Pedido", criarPainelFazerPedido());
		abas.addTab("Pedidos Feitos", criarPainelPedidos());

		abas.addChangeListener(e -> {
			if (abas.getSelectedIndex() == 1) {
				atualizarPedidos();
			}
		});

		add(abas, BorderLayout.CENTER);

		novoPedido();
	}

	private JPanel criarPainelFazerPedido() {

		JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

		JPanel painelSuperior = new JPanel(new BorderLayout());

		lblTitulo = new JLabel("Novo Pedido");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));

		painelSuperior.add(lblTitulo, BorderLayout.WEST);

		lblStatus = new JLabel("Status: Pendente");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));

		painelSuperior.add(lblStatus, BorderLayout.EAST);

		painelPrincipal.add(painelSuperior, BorderLayout.NORTH);

		panelPizzas = new JPanel();
		panelPizzas.setLayout(new BoxLayout(panelPizzas, BoxLayout.Y_AXIS));
		panelPizzas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JScrollPane scrollPizzas = new JScrollPane(panelPizzas);
		scrollPizzas.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		painelPrincipal.add(scrollPizzas, BorderLayout.CENTER);

		JPanel painelInferior = new JPanel(new BorderLayout(5, 5));

		JPanel painelListaItens = new JPanel(new BorderLayout(5, 5));

		JLabel lblItens = new JLabel("Itens do pedido");
		lblItens.setFont(new Font("Tahoma", Font.BOLD, 14));

		painelListaItens.add(lblItens, BorderLayout.NORTH);

		panelItens = new JPanel();
		panelItens.setLayout(new BoxLayout(panelItens, BoxLayout.Y_AXIS));

		JScrollPane scrollItens = new JScrollPane(panelItens);
		scrollItens.setPreferredSize(new Dimension(0, 150));

		painelListaItens.add(scrollItens, BorderLayout.CENTER);

		painelInferior.add(painelListaItens, BorderLayout.CENTER);

		JPanel painelFinal = new JPanel(new BorderLayout());

		lblTotal = new JLabel("Total: R$ 0,00");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblTotal.setHorizontalAlignment(JLabel.LEFT);

		painelFinal.add(lblTotal, BorderLayout.WEST);

		JButton btnSalvar = new JButton("Salvar Pedido");
		btnSalvar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSalvar.setPreferredSize(new Dimension(130, 30));

		btnSalvar.addActionListener(e -> salvarPedido());

		painelFinal.add(btnSalvar, BorderLayout.EAST);

		painelInferior.add(painelFinal, BorderLayout.SOUTH);

		painelPrincipal.add(painelInferior, BorderLayout.SOUTH);

		return painelPrincipal;
	}

	private JPanel criarPainelPedidos() {

		JPanel painel = new JPanel(new BorderLayout(10, 10));

		JLabel titulo = new JLabel("Pedidos para Produção");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 20));

		painel.add(titulo, BorderLayout.NORTH);

		panelPedidos = new JPanel();
		panelPedidos.setLayout(new BoxLayout(panelPedidos, BoxLayout.Y_AXIS));
		panelPedidos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JScrollPane scroll = new JScrollPane(panelPedidos);
		scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		painel.add(scroll, BorderLayout.CENTER);

		return painel;
	}

	private void atualizarPizzas() {

		panelPizzas.removeAll();

		for (Pizza pizza : PizzaController.getPizzas()) {

			panelPizzas.add(criarPainelPizza(pizza));
			panelPizzas.add(Box.createVerticalStrut(10));
		}

		panelPizzas.revalidate();
		panelPizzas.repaint();
	}

	private JPanel criarPainelPizza(Pizza pizza) {

		JPanel painel = new JPanel(new BorderLayout(15, 5));

		painel.setPreferredSize(new Dimension(700, 175));
		painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 175));

		painel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JPanel painelImagem = new JPanel(new BorderLayout());
		painelImagem.setPreferredSize(new Dimension(130, 145));

		JLabel imagem = new JLabel();
		imagem.setHorizontalAlignment(JLabel.CENTER);

		ImageIcon icon = carregarImagem(pizza);

		if (icon != null) {

			Image image = icon.getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH);

			imagem.setIcon(new ImageIcon(image));
		}

		painelImagem.add(imagem, BorderLayout.CENTER);

		painel.add(painelImagem, BorderLayout.WEST);

		JPanel conteudo = new JPanel();
		conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

		conteudo.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblNome = new JLabel(pizza.getNome());
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);

		conteudo.add(lblNome);

		conteudo.add(Box.createVerticalStrut(3));

		JLabel lblPreco = new JLabel(String.format("R$ %.2f", pizza.getPreco()));

		lblPreco.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPreco.setAlignmentX(Component.LEFT_ALIGNMENT);

		conteudo.add(lblPreco);

		conteudo.add(Box.createVerticalStrut(5));

		JLabel lblDescricao = new JLabel("<html>" + pizza.getDescricao() + "</html>");

		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblDescricao.setAlignmentX(Component.LEFT_ALIGNMENT);

		conteudo.add(lblDescricao);

		conteudo.add(Box.createVerticalStrut(8));

		JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

		controles.setAlignmentX(Component.LEFT_ALIGNMENT);

		controles.add(new JLabel("Quantidade:"));

		JSpinner spinnerQuantidade = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));

		spinnerQuantidade.setPreferredSize(new Dimension(55, 25));

		controles.add(spinnerQuantidade);

		controles.add(new JLabel("Tamanho:"));

		JComboBox<String> cbTamanho = new JComboBox<>();

		cbTamanho.addItem("Pequeno (-20%)");
		cbTamanho.addItem("Médio");
		cbTamanho.addItem("Grande (+20%)");
		cbTamanho.addItem("Extra Grande (+40%)");

		cbTamanho.setPreferredSize(new Dimension(145, 25));

		controles.add(cbTamanho);

		conteudo.add(controles);

		conteudo.add(Box.createVerticalStrut(5));

		JPanel painelObservacao = new JPanel(new BorderLayout(5, 0));

		painelObservacao.setAlignmentX(Component.LEFT_ALIGNMENT);

		painelObservacao.add(new JLabel("Observação:"), BorderLayout.WEST);

		JTextField txtObservacao = new JTextField();

		painelObservacao.add(txtObservacao, BorderLayout.CENTER);

		JButton btnAdicionar = new JButton("Adicionar");

		btnAdicionar.setPreferredSize(new Dimension(100, 27));

		btnAdicionar.addActionListener(e -> {

			int quantidade = (int) spinnerQuantidade.getValue();

			int tamanho = cbTamanho.getSelectedIndex() + 1;

			adicionarItem(pizza, quantidade, tamanho, txtObservacao.getText());
		});

		painelObservacao.add(btnAdicionar, BorderLayout.EAST);

		conteudo.add(painelObservacao);

		painel.add(conteudo, BorderLayout.CENTER);

		return painel;
	}

	private void adicionarItem(Pizza pizza, int quantidade, int tamanho, String observacao) {

		String erro = PedidoController.adicionarItem(pedido, pizza, quantidade, tamanho, observacao);

		if (erro != null) {

			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		atualizarItens();

		double preco = PedidoController.calcularPrecoTamanho(pizza, tamanho);

		JOptionPane.showMessageDialog(this,
				quantidade + "x " + pizza.getNome() + " - " + PedidoController.nomeTamanho(tamanho)
						+ "\nPreço unitário: " + String.format("R$ %.2f", preco) + "\nAdicionado ao pedido.");
	}

	private void atualizarItens() {

		panelItens.removeAll();

		if (pedido.getItens().isEmpty()) {

			JLabel vazio = new JLabel("Nenhuma pizza adicionada ao pedido.");

			vazio.setAlignmentX(Component.LEFT_ALIGNMENT);

			vazio.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			panelItens.add(vazio);

		} else {

			for (ItemPedido item : new ArrayList<>(pedido.getItens())) {

				panelItens.add(criarPainelItem(item));

				panelItens.add(Box.createVerticalStrut(5));
			}
		}

		lblTotal.setText(String.format("Total: R$ %.2f", PedidoController.calcularTotal(pedido)));

		panelItens.revalidate();
		panelItens.repaint();
	}

	private JPanel criarPainelItem(ItemPedido item) {

		JPanel painel = new JPanel(new BorderLayout(10, 5));

		painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

		painel.setAlignmentX(Component.LEFT_ALIGNMENT);

		painel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
				BorderFactory.createEmptyBorder(6, 8, 6, 8)));

		String observacao = "";

		if (item.getObservacao() != null && !item.getObservacao().isEmpty()) {

			observacao = "<br>Observação: " + item.getObservacao();
		}

		JLabel informacoes = new JLabel("<html>" + "<b>" + item.getQuantidade() + "x " + item.getPizza().getNome()
				+ "</b>" + "<br>" + "Tamanho: " + PedidoController.nomeTamanho(item.getTamanho()) + "<br>"
				+ "Preço unitário: " + String.format("R$ %.2f", item.getPreco()) + " | Subtotal: "
				+ String.format("R$ %.2f", item.getSubtotal()) + observacao + "</html>");

		painel.add(informacoes, BorderLayout.CENTER);

		JButton btnRemover = new JButton("Remover");

		btnRemover.setPreferredSize(TAMANHO_BOTAO);

		btnRemover.addActionListener(e -> removerItem(item));

		painel.add(btnRemover, BorderLayout.EAST);

		return painel;
	}

	private void removerItem(ItemPedido item) {

		int resposta = JOptionPane.showConfirmDialog(this, "Remover " + item.getPizza().getNome() + " do pedido?",
				"Remover item", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (resposta != JOptionPane.YES_OPTION) {
			return;
		}

		String erro = PedidoController.removerItem(pedido, item);

		if (erro != null) {

			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		atualizarItens();
	}

	private void salvarPedido() {

		if (pedido.getItens().isEmpty()) {

			JOptionPane.showMessageDialog(this, "Adicione pelo menos uma pizza ao pedido.", "Aviso",
					JOptionPane.WARNING_MESSAGE);

			return;
		}

		String erro;

		if ("Em andamento".equals(pedido.getStatus())) {

			erro = PedidoController.salvarEdicao(pedido);

		} else {

			erro = PedidoController.salvarPedido(pedido);
		}

		if (erro != null) {

			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!", "Pedido", JOptionPane.INFORMATION_MESSAGE);

		novoPedido();

		abas.setSelectedIndex(0);
	}

	private void atualizarPedidos() {

		panelPedidos.removeAll();

		List<Pedido> pedidos = PedidoController.getPedidosPendentes();

		if (pedidos.isEmpty()) {

			JLabel vazio = new JLabel("Nenhum pedido pendente.");

			vazio.setAlignmentX(Component.LEFT_ALIGNMENT);

			vazio.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

			panelPedidos.add(vazio);

		} else {

			for (Pedido pedidoAtual : pedidos) {

				panelPedidos.add(criarPainelPedido(pedidoAtual));

				panelPedidos.add(Box.createVerticalStrut(10));
			}
		}

		panelPedidos.revalidate();
		panelPedidos.repaint();
	}

	private JPanel criarPainelPedido(Pedido pedidoAtual) {

		JPanel painel = new JPanel();

		painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

		painel.setAlignmentX(Component.LEFT_ALIGNMENT);

		painel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
				BorderFactory.createEmptyBorder(10, 12, 10, 12)));

		painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		JLabel titulo = new JLabel("Pedido Nº " + pedidoAtual.getId());

		titulo.setFont(new Font("Tahoma", Font.BOLD, 18));

		titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		painel.add(titulo);

		painel.add(Box.createVerticalStrut(5));

		JLabel funcionarioLabel = new JLabel("Funcionário: " + obterNomeFuncionario(pedidoAtual));

		funcionarioLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		painel.add(funcionarioLabel);

		painel.add(Box.createVerticalStrut(5));

		JLabel data = new JLabel("Data: " + pedidoAtual.getData());

		data.setAlignmentX(Component.LEFT_ALIGNMENT);

		painel.add(data);

		painel.add(Box.createVerticalStrut(10));

		JLabel pizzasTitulo = new JLabel("Pizzas do pedido:");

		pizzasTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));

		pizzasTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		painel.add(pizzasTitulo);

		painel.add(Box.createVerticalStrut(5));

		for (ItemPedido item : pedidoAtual.getItens()) {

			JPanel pizzaPainel = criarPizzaPedido(item);

			pizzaPainel.setAlignmentX(Component.LEFT_ALIGNMENT);

			painel.add(pizzaPainel);

			painel.add(Box.createVerticalStrut(4));
		}

		painel.add(Box.createVerticalStrut(5));

		JLabel total = new JLabel(String.format("Total: R$ %.2f", pedidoAtual.getValorTotal()));

		total.setFont(new Font("Tahoma", Font.BOLD, 16));

		total.setAlignmentX(Component.LEFT_ALIGNMENT);

		painel.add(total);

		painel.add(Box.createVerticalStrut(10));

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));

		painelBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);

		JButton btnEditar = new JButton("Editar");

		JButton btnConcluir = new JButton("Concluir");

		JButton btnExcluir = new JButton("Excluir");

		btnEditar.setPreferredSize(TAMANHO_BOTAO);

		btnConcluir.setPreferredSize(TAMANHO_BOTAO);

		btnExcluir.setPreferredSize(TAMANHO_BOTAO);

		btnEditar.addActionListener(e -> editarPedido(pedidoAtual));

		btnConcluir.addActionListener(e -> concluirPedido(pedidoAtual));

		btnExcluir.addActionListener(e -> excluirPedido(pedidoAtual));

		painelBotoes.add(btnEditar);
		painelBotoes.add(btnConcluir);
		painelBotoes.add(btnExcluir);

		painel.add(painelBotoes);

		return painel;
	}

	private JPanel criarPizzaPedido(ItemPedido item) {

		JPanel painel = new JPanel();

		painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

		painel.setAlignmentX(Component.LEFT_ALIGNMENT);

		String texto = item.getQuantidade() + "x " + item.getPizza().getNome() + " - "
				+ PedidoController.nomeTamanho(item.getTamanho()) + " - "
				+ String.format("R$ %.2f", item.getSubtotal());

		JLabel pizza = new JLabel(texto);

		pizza.setAlignmentX(Component.LEFT_ALIGNMENT);

		painel.add(pizza);

		if (item.getObservacao() != null && !item.getObservacao().trim().isEmpty()) {

			JLabel observacao = new JLabel("Observação: " + item.getObservacao());

			observacao.setAlignmentX(Component.LEFT_ALIGNMENT);

			painel.add(observacao);
		}

		return painel;
	}

	private String obterNomeFuncionario(Pedido pedidoAtual) {

		if (pedidoAtual.getFuncionario() == null) {
			return "Não informado";
		}

		return pedidoAtual.getFuncionario().getNome();
	}

	private void editarPedido(Pedido pedidoSelecionado) {

		String erro = PedidoController.iniciarEdicao(pedidoSelecionado);

		if (erro != null) {

			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		pedido = pedidoSelecionado;

		lblTitulo.setText("Editando Pedido Nº " + pedido.getId());

		lblStatus.setText("Status: Em andamento");

		atualizarItens();
		atualizarPizzas();

		abas.setSelectedIndex(0);
	}

	private void concluirPedido(Pedido pedidoSelecionado) {

		String[] formasPagamento = { "Dinheiro", "Pix", "Débito", "Crédito" };

		int forma = JOptionPane.showOptionDialog(this, "Selecione a forma de pagamento:", "Concluir pedido",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, formasPagamento, formasPagamento[0]);

		if (forma < 0) {
			return;
		}

		int resposta = JOptionPane.showConfirmDialog(this,
				String.format("Concluir o pedido Nº %d?\nTotal: R$ %.2f", pedidoSelecionado.getId(),
						pedidoSelecionado.getValorTotal()),
				"Concluir pedido", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (resposta != JOptionPane.YES_OPTION) {
			return;
		}

		String erro = PedidoController.finalizarPedido(pedidoSelecionado, forma + 1);

		if (erro != null) {

			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		JOptionPane.showMessageDialog(this, "Pedido Nº " + pedidoSelecionado.getId() + " concluído com sucesso!",
				"Pedido concluído", JOptionPane.INFORMATION_MESSAGE);

		atualizarPedidos();
	}

	private void excluirPedido(Pedido pedidoSelecionado) {

		int resposta = JOptionPane.showConfirmDialog(this, "Excluir o pedido Nº " + pedidoSelecionado.getId() + "?",
				"Excluir pedido", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (resposta != JOptionPane.YES_OPTION) {
			return;
		}

		String erro = PedidoController.excluirPedido(pedidoSelecionado);

		if (erro != null) {

			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);

			return;
		}

		atualizarPedidos();
	}

	private void novoPedido() {

		pedido = PedidoController.criarPedido(funcionario);

		lblTitulo.setText("Novo Pedido");
		lblStatus.setText("Status: Pendente");

		lblTotal.setText("Total: R$ 0,00");

		atualizarItens();
		atualizarPizzas();
	}

	private ImageIcon carregarImagem(Pizza pizza) {

		if (pizza.getImagem() == null || pizza.getImagem().trim().isEmpty()) {

			return null;
		}

		File arquivo = new File(pizza.getImagem());

		if (!arquivo.exists()) {
			return null;
		}

		return new ImageIcon(arquivo.getAbsolutePath());
	}

	public Pedido getPedido() {
		return pedido;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}
}