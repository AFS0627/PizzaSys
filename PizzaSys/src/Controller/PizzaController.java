package Controller;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Pizza;
import View.TelaPizzas;

public class PizzaController {

    private static List<Pizza> pizzas = new ArrayList<>();
    private static int proximoId = 1;

    public static List<Pizza> getPizzas() {
        return pizzas;
    }

    public static void adicionarPizza(
            TelaPizzas tela,
            String nome,
            String precoTexto,
            String descricao,
            File arquivoImagem) {

        if (nome.trim().isEmpty()) {
            tela.mostrarErro("Digite o nome da pizza.");
            return;
        }

        if (precoTexto.trim().isEmpty()) {
            tela.mostrarErro("Digite o preço.");
            return;
        }

        if (descricao.trim().isEmpty()) {
            tela.mostrarErro("Digite a descrição.");
            return;
        }

        if (arquivoImagem == null) {
            tela.mostrarErro("Selecione uma imagem.");
            return;
        }

        double preco;

        try {
            preco = Double.parseDouble(precoTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            tela.mostrarErro("Digite um preço válido.");
            return;
        }

        int id = proximoId++;
        String caminhoImagem = copiarImagem(arquivoImagem, id);

        if (caminhoImagem == null) {
            tela.mostrarErro("Não foi possível salvar a imagem.");
            return;
        }

        Pizza pizza = new Pizza(
                id,
                nome,
                preco,
                descricao,
                caminhoImagem);

        pizzas.add(pizza);

        tela.limparErro();
        tela.atualizarFeed();
    }

    public static void editarPizza(
            TelaPizzas tela,
            Pizza pizza,
            String nome,
            String precoTexto,
            String descricao,
            File arquivoImagem) {

        if (pizza == null) {
            JOptionPane.showMessageDialog(
                    tela,
                    "Selecione uma pizza para editar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nome.trim().isEmpty()) {
            tela.mostrarErro("Digite o nome da pizza.");
            return;
        }

        if (precoTexto.trim().isEmpty()) {
            tela.mostrarErro("Digite o preço.");
            return;
        }

        if (descricao.trim().isEmpty()) {
            tela.mostrarErro("Digite a descrição.");
            return;
        }

        double preco;

        try {
            preco = Double.parseDouble(precoTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            tela.mostrarErro("Digite um preço válido.");
            return;
        }

        String imagemAntiga = pizza.getImagem();
        String novaImagem = imagemAntiga;

        if (arquivoImagem != null) {

            novaImagem = copiarImagem(arquivoImagem, pizza.getId());

            if (novaImagem == null) {
                tela.mostrarErro("Não foi possível salvar a nova imagem.");
                return;
            }

            apagarImagem(imagemAntiga);
        }

        pizza.setNome(nome);
        pizza.setPreco(preco);
        pizza.setDescricao(descricao);
        pizza.setImagem(novaImagem);

        tela.limparErro();
        tela.atualizarFeed();

        JOptionPane.showMessageDialog(
                tela,
                "Pizza alterada com sucesso!");
    }

    private static void apagarImagem(String caminho) {

        if (caminho == null || caminho.trim().isEmpty()) {
            return;
        }

        File arquivo = new File(caminho);

        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    public static void excluirPizza(
            Component parent,
            Pizza pizza,
            TelaPizzas tela) {

        if (pizza == null) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Selecione uma pizza para excluir.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                parent,
                "Tem certeza que deseja excluir a pizza "
                        + pizza.getNome() + "?",
                "Excluir pizza",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        if (pizza.getImagem() != null) {
            File arquivoImagem = new File(pizza.getImagem());

            if (arquivoImagem.exists()) {
                arquivoImagem.delete();
            }
        }

        pizzas.remove(pizza);

        tela.setPizzaSelecionada(null);
        tela.atualizarFeed();

        JOptionPane.showMessageDialog(
                parent,
                "Pizza excluída com sucesso!");
    }

    private static String copiarImagem(File arquivoOrigem, int id) {

        try {
            File pastaImagens = new File("imagens");

            if (!pastaImagens.exists()) {
                pastaImagens.mkdirs();
            }

            String extensao = obterExtensao(arquivoOrigem.getName());

            File arquivoDestino = new File(
                    pastaImagens,
                    "pizza_" + id + extensao);

            Files.copy(
                    arquivoOrigem.toPath(),
                    arquivoDestino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return arquivoDestino.getPath();

        } catch (IOException e) {
            return null;
        }
    }

    private static String obterExtensao(String nomeArquivo) {

        int ponto = nomeArquivo.lastIndexOf(".");

        if (ponto >= 0) {
            return nomeArquivo.substring(ponto);
        }

        return ".png";
    }
}