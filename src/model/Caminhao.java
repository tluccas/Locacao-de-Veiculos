package model;

public class Caminhao extends Veiculo{

    public Caminhao(String placa, String modelo, int ano, boolean disponivel){
        super(placa, modelo, ano, disponivel);
    }

    @Override
    public double calcularPrecoLocacao(int dias){
        return dias * 100;
    }

}
