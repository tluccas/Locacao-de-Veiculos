package model;

public class Atendente extends Usuario{

    public Atendente(String usuario, String senha, String tipo) {
        super(usuario, senha, tipo);
    }

    @Override
    public String getTipo(){
        return "Atendente";
    }
}
