package model;

public class Carro extends Veiculo{

    public Carro(String placa, String modelo, int ano, boolean disponivel) {
        super(placa, modelo, ano, disponivel);
    }

    @Override
    public double calcularPrecoLocacao(int dias){
        return dias * 80;
    }

}
