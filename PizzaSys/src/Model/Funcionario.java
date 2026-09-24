package Model;

import java.util.ArrayList;
import java.util.List;

public class Funcionario {

	private int id;

	private String nome;

	private String login;

	private String senha;

	private int funcao;
	
	private double salario;

	private List<Notificacao> notificacoes;

	public Funcionario(int id, String nome, String login, String senha, int funcao, double salario) {

		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.funcao = funcao;
		this.salario = salario;

		this.notificacoes = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public int getFuncao() {
		return funcao;
	}

	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public void adicionarNotificacao(Notificacao notificacao) {
		notificacoes.add(notificacao);
	}

	public void removerNotificacao(Notificacao notificacao) {
		notificacoes.remove(notificacao);
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public void setFuncao(int funcao) {
		this.funcao = funcao;
	}
}