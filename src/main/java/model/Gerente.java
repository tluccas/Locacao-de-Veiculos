package model;

public class Gerente extends Usuario{

    public Gerente(String usuario, String senha, String tipo){
        super(usuario, senha, tipo);
    }

    @Override
    public String getTipo(){
        return "Gerente";
    }
}
