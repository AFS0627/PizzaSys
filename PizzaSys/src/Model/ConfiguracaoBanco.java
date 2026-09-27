package Model;

public class ConfiguracaoBanco {

    private String servidor;
    private String usuario;
    private String senha;
    private String banco;

    public ConfiguracaoBanco(String servidor, String usuario, String senha, String banco) {
        this.servidor = servidor;
        this.usuario = usuario;
        this.senha = senha;
        this.banco = banco;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
}