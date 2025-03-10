package model;

public class Administrador extends Usuario{

    public Administrador(String usuario, String senha, String tipo) {
        super(usuario, senha, tipo);
    }

    @Override
    public String getTipo(){
        return "Administrador";
    }
}
