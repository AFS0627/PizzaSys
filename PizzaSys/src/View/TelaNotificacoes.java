package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import Controller.NotificacoesController;
import Model.Funcionario;
import Model.Notificacao;

public class TelaNotificacoes extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel panelFeed;
	private Notificacao notificacaoSelecionada;
	private JLabel lblErro;
	private Funcionario funcionario;
	private TelaPrincipal telaPrincipal;
	private Map<Notificacao, JPanel> paineisNotificacao = new HashMap<Notificacao, JPanel>();

	public TelaNotificacoes(Funcionario funcionario, TelaPrincipal telaPrincipal) {
		this.funcionario = funcionario;
		this.telaPrincipal = telaPrincipal;
		setLayout(new BorderLayout(10, 10));
		JPanel panelSuperior = new JPanel(new BorderLayout());
		JLabel lblTitulo = new JLabel("Notificações");
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
		JButton btnMarcarLida = new JButton("Marcar como lida");
		btnMarcarLida.addActionListener(e -> marcarComoLida());
		panelBotoes.add(btnMarcarLida);
		JButton btnMarcarNaoLida = new JButton("Marcar como não lida");
		btnMarcarNaoLida.addActionListener(e -> marcarComoNaoLida());
		panelBotoes.add(btnMarcarNaoLida);
		JButton btnLimparTodas = new JButton("Limpar todas");
		btnLimparTodas.addActionListener(e -> limparTodas());
		panelBotoes.add(btnLimparTodas);
		add(panelBotoes, BorderLayout.SOUTH);
		atualizarFeed();
	}

	public void atualizarFeed() {
		panelFeed.removeAll();
		paineisNotificacao.clear();
		notificacaoSelecionada = null;
		if (funcionario == null) {
			panelFeed.revalidate();
			panelFeed.repaint();
			return;
		}
		try {
			List<Notificacao> notificacoes = NotificacoesController.getNotificacoes(funcionario);
			for (Notificacao notificacao : notificacoes) {
				JPanel painelNotificacao = criarPainelNotificacao(notificacao);
				paineisNotificacao.put(notificacao, painelNotificacao);
				panelFeed.add(painelNotificacao);
				panelFeed.add(new Box.Filler(new Dimension(0, 10), new Dimension(0, 10), new Dimension(0, 10)));
			}
			limparErro();
		} catch (RuntimeException e) {
			mostrarErro("Não foi possível carregar as notificações.");
		}
		panelFeed.revalidate();
		panelFeed.repaint();
		atualizarContador();
	}

	private JPanel criarPainelNotificacao(Notificacao notificacao) {
		JPanel painel = new JPanel(new BorderLayout(15, 5));
		painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
		painel.setPreferredSize(new Dimension(600, 120));
		painel.setBorder(criarBordaNotificacao(notificacao));
		JPanel informacoes = new JPanel();
		informacoes.setLayout(new BoxLayout(informacoes, BoxLayout.Y_AXIS));
		JLabel lblTitulo = new JLabel(notificacao.getTitulo());
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		informacoes.add(lblTitulo);
		JLabel lblMensagem = new JLabel("<html>" + notificacao.getMensagem().replace("\n", "<br>") + "</html>");
		lblMensagem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		informacoes.add(lblMensagem);
		JLabel lblStatus = new JLabel(notificacao.isLida() ? "Lida" : "Não lida");
		lblStatus.setFont(new Font("Tahoma", Font.ITALIC, 12));
		informacoes.add(lblStatus);
		painel.add(informacoes, BorderLayout.CENTER);
		adicionarListenerSelecao(painel, notificacao);
		return painel;
	}

	private javax.swing.border.Border criarBordaNotificacao(Notificacao notificacao) {
		return BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(notificacao.isLida() ? Color.LIGHT_GRAY : Color.BLUE),
				BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	private void adicionarListenerSelecao(java.awt.Component componente, Notificacao notificacao) {
		componente.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				selecionarNotificacao(notificacao);
			}
		});
		if (componente instanceof JPanel) {
			JPanel panel = (JPanel) componente;
			for (java.awt.Component filho : panel.getComponents()) {
				adicionarListenerSelecao(filho, notificacao);
			}
		}
	}

	private void selecionarNotificacao(Notificacao notificacao) {
		if (notificacaoSelecionada != null) {
			JPanel anterior = paineisNotificacao.get(notificacaoSelecionada);
			if (anterior != null) {
				anterior.setBorder(criarBordaNotificacao(notificacaoSelecionada));
			}
		}
		notificacaoSelecionada = notificacao;
		JPanel atual = paineisNotificacao.get(notificacao);
		if (atual != null) {
			atual.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE, 2),
					BorderFactory.createEmptyBorder(9, 9, 9, 9)));
		}
	}

	private void marcarComoLida() {
		if (notificacaoSelecionada == null) {
			JOptionPane.showMessageDialog(this, "Selecione uma notificação.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			NotificacoesController.marcarComoLida(notificacaoSelecionada);
			atualizarFeed();
		} catch (RuntimeException e) {
			mostrarErro("Não foi possível marcar a notificação como lida.");
		}
	}

	private void marcarComoNaoLida() {
		if (notificacaoSelecionada == null) {
			JOptionPane.showMessageDialog(this, "Selecione uma notificação.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			NotificacoesController.marcarComoNaoLida(notificacaoSelecionada);
			atualizarFeed();
		} catch (RuntimeException e) {
			mostrarErro("Não foi possível marcar a notificação como não lida.");
		}
	}

	private void limparTodas() {
		if (funcionario == null) {
			return;
		}
		int resposta = JOptionPane.showConfirmDialog(this, "Deseja excluir todas as notificações?", "Confirmar",
				JOptionPane.YES_NO_OPTION);
		if (resposta != JOptionPane.YES_OPTION) {
			return;
		}
		try {
			NotificacoesController.excluirTodas(funcionario);
			atualizarFeed();
		} catch (RuntimeException e) {
			mostrarErro("Não foi possível excluir as notificações.");
		}
	}

	private void atualizarContador() {
		if (telaPrincipal != null) {
			telaPrincipal.atualizarContadorNotificacoes();
		}
	}

	public Notificacao getNotificacaoSelecionada() {
		return notificacaoSelecionada;
	}

	public void setNotificacaoSelecionada(Notificacao notificacao) {
		this.notificacaoSelecionada = notificacao;
	}

	public void mostrarErro(String mensagem) {
		lblErro.setText(mensagem);
	}

	public void limparErro() {
		lblErro.setText(" ");
	}
}