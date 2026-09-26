package Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Model.Movimentacao;
import Model.Pedido;

public class RelatoriosController {

    private static final List<Movimentacao> movimentacoes =
            new ArrayList<>();

    public static void registrarMovimentacao(
            Pedido pedido,
            String acao) {

        if (pedido == null) {
            return;
        }

        movimentacoes.add(
                new Movimentacao(
                        pedido.getFuncionario(),
                        acao,
                        pedido.getId()));
    }

    public static void registrarMovimentacao(
            Model.Funcionario funcionario,
            String acao,
            int idPedido) {

        movimentacoes.add(
                new Movimentacao(
                        funcionario,
                        acao,
                        idPedido));
    }

    public static List<Movimentacao> getMovimentacoes() {

        List<Movimentacao> lista =
                new ArrayList<>(movimentacoes);

        lista.sort(
                Comparator.comparing(
                        Movimentacao::getDataHora)
                        .reversed());

        return lista;
    }

    public static List<Movimentacao> getMovimentacoesFuncionario(
            Model.Funcionario funcionario) {

        List<Movimentacao> resultado =
                new ArrayList<>();

        if (funcionario == null) {
            return resultado;
        }

        for (Movimentacao movimentacao : movimentacoes) {

            if (movimentacao.getFuncionario() == funcionario) {
                resultado.add(movimentacao);
            }
        }

        resultado.sort(
                Comparator.comparing(
                        Movimentacao::getDataHora)
                        .reversed());

        return resultado;
    }

    public static double getFaturamento() {

        double total = 0;

        for (Pedido pedido :
                PedidoController.getPedidosConcluidos()) {

            total += pedido.getValorTotal();
        }

        return total;
    }

    public static int getQuantidadePedidos() {

        return PedidoController
                .getPedidosConcluidos()
                .size();
    }

    public static int getQuantidadePagamentos(
            int formaPagamento) {

        int quantidade = 0;

        for (Pedido pedido :
                PedidoController.getPedidosConcluidos()) {

            if (pedido.getFormaPagamento()
                    == formaPagamento) {

                quantidade++;
            }
        }

        return quantidade;
    }

    public static double getFaturamentoPagamento(
            int formaPagamento) {

        double total = 0;

        for (Pedido pedido :
                PedidoController.getPedidosConcluidos()) {

            if (pedido.getFormaPagamento()
                    == formaPagamento) {

                total += pedido.getValorTotal();
            }
        }

        return total;
    }

    public static int getQuantidadeDinheiro() {
        return getQuantidadePagamentos(1);
    }

    public static int getQuantidadePix() {
        return getQuantidadePagamentos(2);
    }

    public static int getQuantidadeDebito() {
        return getQuantidadePagamentos(3);
    }

    public static int getQuantidadeCredito() {
        return getQuantidadePagamentos(4);
    }

    public static double getFaturamentoDinheiro() {
        return getFaturamentoPagamento(1);
    }

    public static double getFaturamentoPix() {
        return getFaturamentoPagamento(2);
    }

    public static double getFaturamentoDebito() {
        return getFaturamentoPagamento(3);
    }

    public static double getFaturamentoCredito() {
        return getFaturamentoPagamento(4);
    }

    public static String nomeFormaPagamento(
            int formaPagamento) {

        switch (formaPagamento) {

        case 1:
            return "Dinheiro";

        case 2:
            return "Pix";

        case 3:
            return "Débito";

        case 4:
            return "Crédito";

        default:
            return "Não informado";
        }
    }
}