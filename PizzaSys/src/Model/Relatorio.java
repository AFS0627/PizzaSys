package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Relatorio {

	private int id;
	private Funcionario funcionario;
	private String acao;
	private int idPedido;
	private LocalDateTime dataHora;

	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	public Relatorio(Funcionario funcionario, String acao, int idPedido) {
		this.funcionario = funcionario;
		this.acao = acao;
		this.idPedido = idPedido;
		this.dataHora = LocalDateTime.now();
	}

	public Relatorio(int id, Funcionario funcionario, String acao, int idPedido, LocalDateTime dataHora) {
		this.id = id;
		this.funcionario = funcionario;
		this.acao = acao;
		this.idPedido = idPedido;
		this.dataHora = dataHora;
	}

	public int getId() {
		return id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public String getNomeFuncionario() {
		if (funcionario == null) {
			return "Não informado";
		}

		return funcionario.getNome();
	}

	public String getAcao() {
		return acao;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public String getDataHoraFormatada() {
		return dataHora.format(FORMATO);
	}
}