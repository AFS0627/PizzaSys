package Model;

public class Pizza {

    private int id;
    private String nome;
    private double preco;
    private String descricao;
    private byte[] imagem;

    public Pizza(int id, String nome, double preco, String descricao, byte[] imagem) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}