package model;

public class Gerente extends Usuario{

    public Gerente(String usuario, String senha){
        super(usuario, senha);
    }

    @Override
    public String getTipo(){
        return "Gerente";
    }
}
