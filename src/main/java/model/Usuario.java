package model;

public abstract class Usuario {

    private String usuario;
    private String senha;
    private String tipo;

    public Usuario(String usuario, String senha, String tipo) {
        this.usuario = usuario;
        this.senha = senha;
        this.tipo = tipo;
    }

    public String getNome() {
        return usuario;
    }
    public void setNome(String usuario) {
        this.usuario = usuario;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return "Usuario [nome=" + usuario + ", senha=" + senha + "]";
    }
}
