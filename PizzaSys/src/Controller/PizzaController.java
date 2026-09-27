package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import DAO.PizzaDAO;
import Model.Pizza;

public class PizzaController {

    public static List<Pizza> getPizzas() {

        try {
            return PizzaDAO.buscarTodos();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Pizza>();
        }
    }

    public static String adicionarPizza(
            String nome,
            String precoTexto,
            String descricao,
            File arquivoImagem) {

        if (nome == null || nome.trim().isEmpty()) {
            return "Digite o nome da pizza.";
        }

        if (precoTexto == null || precoTexto.trim().isEmpty()) {
            return "Digite o preço.";
        }

        if (descricao == null || descricao.trim().isEmpty()) {
            return "Digite a descrição.";
        }

        if (arquivoImagem == null) {
            return "Selecione uma imagem.";
        }

        double preco;

        try {
            preco = Double.parseDouble(
                    precoTexto.replace(",", ".").trim()
            );

        } catch (NumberFormatException e) {
            return "Digite um preço válido.";
        }

        if (preco < 0) {
            return "O preço não pode ser negativo.";
        }

        byte[] imagem;

        try {
            imagem = Files.readAllBytes(arquivoImagem.toPath());

        } catch (IOException e) {
            e.printStackTrace();
            return "Não foi possível ler a imagem.";
        }

        Pizza pizza = new Pizza(
                0,
                nome.trim(),
                preco,
                descricao.trim(),
                imagem
        );

        try {

            int id = PizzaDAO.inserir(pizza);

            pizza = new Pizza(
                    id,
                    nome.trim(),
                    preco,
                    descricao.trim(),
                    imagem
            );

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return "Não foi possível salvar a pizza no banco de dados.";
        }
    }

    public static String editarPizza(
            Pizza pizza,
            String nome,
            String precoTexto,
            String descricao,
            File arquivoImagem) {

        if (pizza == null) {
            return "Selecione uma pizza para editar.";
        }

        if (nome == null || nome.trim().isEmpty()) {
            return "Digite o nome da pizza.";
        }

        if (precoTexto == null || precoTexto.trim().isEmpty()) {
            return "Digite o preço.";
        }

        if (descricao == null || descricao.trim().isEmpty()) {
            return "Digite a descrição.";
        }

        double preco;

        try {
            preco = Double.parseDouble(
                    precoTexto.replace(",", ".").trim()
            );

        } catch (NumberFormatException e) {
            return "Digite um preço válido.";
        }

        if (preco < 0) {
            return "O preço não pode ser negativo.";
        }

        byte[] imagem = pizza.getImagem();

        if (arquivoImagem != null) {

            try {
                imagem = Files.readAllBytes(
                        arquivoImagem.toPath()
                );

            } catch (IOException e) {
                e.printStackTrace();
                return "Não foi possível ler a nova imagem.";
            }
        }

        pizza.setNome(nome.trim());
        pizza.setPreco(preco);
        pizza.setDescricao(descricao.trim());
        pizza.setImagem(imagem);

        try {

            PizzaDAO.atualizar(pizza);

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return "Não foi possível atualizar a pizza no banco de dados.";
        }
    }

    public static String excluirPizza(Pizza pizza) {

        if (pizza == null) {
            return "Selecione uma pizza para excluir.";
        }

        if (pizza.getId() <= 0) {
            return "Pizza inválida.";
        }

        try {

            PizzaDAO.excluir(pizza.getId());

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return "Não foi possível excluir a pizza.";
        }
    }

    public static Pizza buscarPizza(int id) {

        if (id <= 0) {
            return null;
        }

        try {
            return PizzaDAO.buscarPorId(id);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}