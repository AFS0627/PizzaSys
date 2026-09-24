package Model;

public class Funcionario {
	private int id;
	private String nome;
	private String login;
	private String senha;
	private int funcao;

	public Funcionario(int id, String nome, String login, String senha, int funcao) {
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.funcao = funcao;
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

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setFuncao(int funcao) {
		this.funcao = funcao;
	}
}
