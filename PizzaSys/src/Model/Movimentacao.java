package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimentacao {

    private Funcionario funcionario;
    private String acao;
    private int idPedido;
    private LocalDateTime dataHora;

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Movimentacao(Funcionario funcionario, String acao, int idPedido) {
        this.funcionario = funcionario;
        this.acao = acao;
        this.idPedido = idPedido;
        this.dataHora = LocalDateTime.now();
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
