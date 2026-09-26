package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
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
	private JLabel lblTotal;
	private JLabel lblStatus;

	public TelaPedido(Funcionario funcionario) {
		this.funcionario = funcionario;
		this.pedido = PedidoController.criarPedido(funcionario);
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel panelSuperior = new JPanel(new BorderLayout());
		JLabel lblTitulo = new JLabel("Novo Pedido");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelSuperior.add(lblTitulo, BorderLayout.WEST);
		lblStatus = new JLabel("Status: Pendente");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelSuperior.add(lblStatus, BorderLayout.EAST);
		add(panelSuperior, BorderLayout.NORTH);
		panelPizzas = new JPanel();
		panelPizzas.setLayout(new BoxLayout(panelPizzas, BoxLayout.Y_AXIS));
		panelPizzas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JScrollPane scrollPizzas = new JScrollPane(panelPizzas);
		scrollPizzas.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		add(scrollPizzas, BorderLayout.CENTER);
		JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
		JPanel panelListaItens = new JPanel(new BorderLayout(5, 5));
		JLabel lblItens = new JLabel("Itens do pedido");
		lblItens.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelListaItens.add(lblItens, BorderLayout.NORTH);
		panelItens = new JPanel();
		panelItens.setLayout(new BoxLayout(panelItens, BoxLayout.Y_AXIS));
		JScrollPane scrollItens = new JScrollPane(panelItens);
		scrollItens.setPreferredSize(new Dimension(0, 150));
		panelListaItens.add(scrollItens, BorderLayout.CENTER);
		panelInferior.add(panelListaItens, BorderLayout.CENTER);
		JPanel panelFinal = new JPanel(new BorderLayout());
		lblTotal = new JLabel("Total: R$ 0,00");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 17));
		panelFinal.add(lblTotal, BorderLayout.WEST);
		JButton btnFinalizar = new JButton("Finalizar Pedido");
		btnFinalizar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnFinalizar.addActionListener(e -> finalizarPedido());
		panelFinal.add(btnFinalizar, BorderLayout.EAST);
		panelInferior.add(panelFinal, BorderLayout.SOUTH);
		add(panelInferior, BorderLayout.SOUTH);
		atualizarPizzas();
		atualizarItens();
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
		JLabel lblNome = new JLabel(pizza.getNome());
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNome.setAlignmentX(LEFT_ALIGNMENT);
		conteudo.add(lblNome);
		conteudo.add(Box.createVerticalStrut(3));
		JLabel lblPreco = new JLabel(String.format("R$ %.2f", pizza.getPreco()));
		lblPreco.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPreco.setAlignmentX(LEFT_ALIGNMENT);
		conteudo.add(lblPreco);
		conteudo.add(Box.createVerticalStrut(5));
		JLabel lblDescricao = new JLabel("<html>" + pizza.getDescricao() + "</html>");
		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDescricao.setAlignmentX(LEFT_ALIGNMENT);
		conteudo.add(lblDescricao);
		conteudo.add(Box.createVerticalStrut(8));
		JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		controles.setAlignmentX(LEFT_ALIGNMENT);
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
		painelObservacao.setAlignmentX(LEFT_ALIGNMENT);
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

	private void finalizarPedido() {
		if (pedido.getItens().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Adicione pelo menos uma pizza ao pedido.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		int resposta = JOptionPane.showConfirmDialog(this,
				String.format("Finalizar pedido no valor de R$ %.2f?", pedido.getValorTotal()), "Finalizar pedido",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (resposta != JOptionPane.YES_OPTION) {
			return;
		}
		String erro = PedidoController.finalizarPedido(pedido);
		if (erro != null) {
			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int idPedidoFinalizado = pedido.getId();
		double totalPedidoFinalizado = pedido.getValorTotal();
		JOptionPane.showMessageDialog(this, "Pedido finalizado com sucesso!" + "\nPedido Nº " + idPedidoFinalizado
				+ "\nTotal: " + String.format("R$ %.2f", totalPedidoFinalizado));
		novoPedido();
	}

	private void novoPedido() {
		pedido = PedidoController.criarPedido(funcionario);
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