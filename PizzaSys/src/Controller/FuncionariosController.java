package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.FuncionarioDAO;
import Model.Funcionario;

public class FuncionariosController {

    public static List<Funcionario> getFuncionarios() {
        try {
            return FuncionarioDAO.buscarTodos();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Não foi possível carregar os funcionários.",
                    e
            );
        }
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
                0,
                nome.trim(),
                login.trim(),
                senha,
                funcao,
                salario
        );

        try {
            FuncionarioDAO.inserir(funcionario);
            return null;
        } catch (SQLException e) {
            return "Não foi possível cadastrar o funcionário.";
        }
    }

    public static String alterarFuncionario(
            int id,
            String nome,
            String login,
            String senha,
            int funcao,
            String salarioTexto) {

        Funcionario funcionario;

        try {
            funcionario = FuncionarioDAO.buscarPorId(id);
        } catch (SQLException e) {
            return "Não foi possível localizar o funcionário.";
        }

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

        try {
            FuncionarioDAO.atualizar(funcionario);
            return null;
        } catch (SQLException e) {
            return "Não foi possível alterar o funcionário.";
        }
    }

    public static boolean excluirFuncionario(int id) {
        try {
            Funcionario funcionario = FuncionarioDAO.buscarPorId(id);

            if (funcionario == null) {
                return false;
            }

            FuncionarioDAO.excluir(id);
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public static Funcionario buscarFuncionario(int id) {
        try {
            return FuncionarioDAO.buscarPorId(id);
        } catch (SQLException e) {
            return null;
        }
    }

    public static String nomeFuncao(int funcao) {
        switch (funcao) {
        case 1:
            return "Atendente";
        case 2:
            return "Pizzaiolo";
        case 3:
            return "Administrador";
        default:
            return "Desconhecida";
        }
    }
}