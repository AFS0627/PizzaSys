package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Funcionario;

public class FuncionariosController {

	private static List<Funcionario> funcionarios = new ArrayList<>();
	private static int proximoId = 1;

	public static List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public static String cadastrarFuncionario(
			String nome,
			String login,
			String senha,
			int funcao,
			String salarioTexto) {

		if (nome == null || nome.trim().isEmpty()) {
			return "Digite o nome do funcionário.";
		}

		if (login == null || login.trim().isEmpty()) {
			return "Digite o login.";
		}

		if (senha == null || senha.trim().isEmpty()) {
			return "Digite a senha.";
		}

		if (salarioTexto == null || salarioTexto.trim().isEmpty()) {
			return "Digite o salário.";
		}

		double salario;

		try {
			salario = Double.parseDouble(
					salarioTexto.replace(",", "."));
		} catch (NumberFormatException e) {
			return "Digite um salário válido.";
		}

		Funcionario funcionario = new Funcionario(
				proximoId++,
				nome.trim(),
				login.trim(),
				senha,
				funcao,
				salario);

		funcionarios.add(funcionario);

		return null;
	}


			public static String alterarFuncionario(
			        int id,
			        String nome,
			        String login,
			        String senha,
			        int funcao,
			        String salarioTexto) {

		Funcionario funcionario = buscarFuncionario(id);

		if (funcionario == null) {
			return "Funcionário não encontrado.";
		}

		if (nome == null || nome.trim().isEmpty()) {
			return "Digite o nome do funcionário.";
		}

		if (login == null || login.trim().isEmpty()) {
			return "Digite o login.";
		}

		if (senha == null || senha.trim().isEmpty()) {
			return "Digite a senha.";
		}

		if (salarioTexto == null || salarioTexto.trim().isEmpty()) {
			return "Digite o salário.";
		}

		double salario;

		try {
			salario = Double.parseDouble(
					salarioTexto.replace(",", "."));
		} catch (NumberFormatException e) {
			return "Digite um salário válido.";
		}

		funcionario.setNome(nome.trim());
		funcionario.setLogin(login.trim());
		funcionario.setSenha(senha);
		funcionario.setFuncao(funcao);
		funcionario.setSalario(salario);

		return null;
	}

	public static boolean excluirFuncionario(int id) {

		Funcionario funcionario = buscarFuncionario(id);

		if (funcionario == null) {
			return false;
		}

		return funcionarios.remove(funcionario);
	}

	public static Funcionario buscarFuncionario(int id) {

		for (Funcionario funcionario : funcionarios) {

			if (funcionario.getId() == id) {
				return funcionario;
			}
		}

		return null;
	}

	public static String nomeFuncao(int funcao) {

		switch (funcao) {

		case 1:
			return "Atendente";

		case 2:
			return "Gerente";

		case 3:
			return "Administrador";

		default:
			return "Desconhecida";
		}
	}
}
