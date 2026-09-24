package Model;

public class Notificacao {

	private int id;

	private String titulo;

	private String mensagem;

	private boolean lida;

	public Notificacao(int id, String titulo, String mensagem) {

		this.id = id;
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.lida = false;
	}

	public int getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public boolean isLida() {
		return lida;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void marcarComoLida() {
		this.lida = true;
	}

	public void marcarComoNaoLida() {
		this.lida = false;
	}
}