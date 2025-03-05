package model;

public class Moto extends Veiculo{

    public Moto(String placa, String modelo, int ano, boolean disponivel){
        super(placa, modelo, ano, disponivel);
    }

    @Override
    public double calcularPrecoLocacao(int dias){
        return dias * 50;
    }
}
