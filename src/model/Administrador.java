package model;

public class Administrador extends Usuario{

    public Administrador(String usuario, String senha) {
        super(usuario, senha);
    }

    @Override
    public String getTipo(){
        return "Administrador";
    }
}
