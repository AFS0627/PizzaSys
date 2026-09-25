package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Controller.PizzaController;
import Model.Pizza;

public class TelaPizzas extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelFeed;
	private Pizza pizzaSelecionada;
	private JLabel lblErro;
	private Map<Pizza, JPanel> paineisPizza = new HashMap<>();

	public TelaPizzas() {

		setLayout(new BorderLayout(10, 10));

		JPanel panelSuperior = new JPanel(new BorderLayout());

		JLabel lblTitulo = new JLabel("Pizzas");

		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));

		panelSuperior.add(lblTitulo, BorderLayout.WEST);

		lblErro = new JLabel(" ");

		lblErro.setForeground(Color.RED);

		panelSuperior.add(lblErro, BorderLayout.CENTER);

		add(panelSuperior, BorderLayout.NORTH);

		panelFeed = new JPanel();

		panelFeed.setLayout(new BoxLayout(panelFeed, BoxLayout.Y_AXIS));

		panelFeed.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JScrollPane scrollPane = new JScrollPane(panelFeed);

		scrollPane.setBorder(null);

		add(scrollPane, BorderLayout.CENTER);

		JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton btnAdicionar = new JButton("Adicionar Pizza");

		btnAdicionar.addActionListener(e -> abrirFormularioAdicionar());

		panelBotoes.add(btnAdicionar);

		JButton btnEditar = new JButton("Editar");

		btnEditar.addActionListener(e -> abrirFormularioEditar());

		panelBotoes.add(btnEditar);

		JButton btnExcluir = new JButton("Excluir");

		btnExcluir.addActionListener(e -> PizzaController.excluirPizza(this, pizzaSelecionada, this));

		panelBotoes.add(btnExcluir);

		add(panelBotoes, BorderLayout.SOUTH);

		atualizarFeed();
	}

	public void atualizarFeed() {

		panelFeed.removeAll();
		paineisPizza.clear();

		for (Pizza pizza : PizzaController.getPizzas()) {

			JPanel painelPizza = criarPainelPizza(pizza);

			paineisPizza.put(pizza, painelPizza);

			panelFeed.add(painelPizza);

			panelFeed.add(new Box.Filler(new Dimension(0, 10), new Dimension(0, 10), new Dimension(0, 10)));
		}

		panelFeed.revalidate();
		panelFeed.repaint();
	}

	private JPanel criarPainelPizza(Pizza pizza) {

		JPanel painel = new JPanel(new BorderLayout(15, 5));

		painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

		painel.setPreferredSize(new Dimension(600, 140));

		painel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel imagem = new JLabel();

		ImageIcon icon = carregarImagem(pizza);

		if (icon != null) {

			Image image = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);

			imagem.setIcon(new ImageIcon(image));
		}

		painel.add(imagem, BorderLayout.WEST);

		JPanel informacoes = new JPanel();

		informacoes.setLayout(new BoxLayout(informacoes, BoxLayout.Y_AXIS));

		JLabel lblNome = new JLabel(pizza.getNome());

		lblNome.setFont(new Font("Tahoma", Font.BOLD, 18));

		informacoes.add(lblNome);

		JLabel lblDescricao = new JLabel("<html>Descrição: " + pizza.getDescricao() + "</html>");

		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 14));

		informacoes.add(lblDescricao);

		JLabel lblPreco = new JLabel(String.format("R$ %.2f", pizza.getPreco()));

		lblPreco.setFont(new Font("Tahoma", Font.BOLD, 15));

		informacoes.add(lblPreco);

		painel.add(informacoes, BorderLayout.CENTER);

		adicionarListenerSelecao(painel, pizza);

		return painel;
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

	private void adicionarListenerSelecao(java.awt.Component componente, Pizza pizza) {

		componente.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				selecionarPizza(pizza);
			}
		});

		if (componente instanceof JPanel) {

			JPanel panel = (JPanel) componente;

			for (java.awt.Component filho : panel.getComponents()) {

				adicionarListenerSelecao(filho, pizza);
			}
		}
	}

	private void selecionarPizza(Pizza pizza) {

		if (pizzaSelecionada != null) {

			JPanel anterior = paineisPizza.get(pizzaSelecionada);

			if (anterior != null) {

				anterior.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
						BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			}
		}

		pizzaSelecionada = pizza;

		JPanel atual = paineisPizza.get(pizza);

		if (atual != null) {

			atual.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE, 2),
					BorderFactory.createEmptyBorder(9, 9, 9, 9)));
		}
	}

	private void abrirFormularioAdicionar() {

		JTextField txtNome = new JTextField();

		JTextField txtPreco = new JTextField();

		JTextField txtDescricao = new JTextField();

		JPanel painel = new JPanel(new java.awt.GridLayout(3, 2, 5, 5));

		painel.add(new JLabel("Nome:"));

		painel.add(txtNome);

		painel.add(new JLabel("Preço:"));

		painel.add(txtPreco);

		painel.add(new JLabel("Descrição:"));

		painel.add(txtDescricao);

		int resposta = JOptionPane.showConfirmDialog(this, painel, "Adicionar Pizza", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (resposta != JOptionPane.OK_OPTION) {
			return;
		}

		PizzaController.adicionarPizza(this, txtNome.getText(), txtPreco.getText(), txtDescricao.getText());
	}

	private void abrirFormularioEditar() {

		if (pizzaSelecionada == null) {

			JOptionPane.showMessageDialog(this, "Selecione uma pizza para editar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);

			return;
		}

		JTextField txtNome = new JTextField(pizzaSelecionada.getNome());

		JTextField txtPreco = new JTextField(String.valueOf(pizzaSelecionada.getPreco()));

		JTextField txtDescricao = new JTextField(pizzaSelecionada.getDescricao());

		JPanel painel = new JPanel(new java.awt.GridLayout(3, 2, 5, 5));

		painel.add(new JLabel("Nome:"));

		painel.add(txtNome);

		painel.add(new JLabel("Preço:"));

		painel.add(txtPreco);

		painel.add(new JLabel("Descrição:"));

		painel.add(txtDescricao);

		int resposta = JOptionPane.showConfirmDialog(this, painel, "Editar Pizza", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (resposta != JOptionPane.OK_OPTION) {
			return;
		}

		PizzaController.editarPizza(this, pizzaSelecionada, txtNome.getText(), txtPreco.getText(),
				txtDescricao.getText());
	}

	public Pizza getPizzaSelecionada() {
		return pizzaSelecionada;
	}

	public void setPizzaSelecionada(Pizza pizza) {

		this.pizzaSelecionada = pizza;
	}

	public void mostrarErro(String mensagem) {

		lblErro.setText(mensagem);
	}

	public void limparErro() {

		lblErro.setText(" ");
	}
}