package model;

public class Atendente extends Usuario{

    public Atendente(String usuario, String senha) {
        super(usuario, senha);
    }

    @Override
    public String getTipo(){
        return "Atendente";
    }
}
