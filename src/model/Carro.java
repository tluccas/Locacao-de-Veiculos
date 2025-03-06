package model;


public class Carro extends Veiculo{

    public Carro(String placa, String modelo, int ano) {
        super(placa, modelo, ano);
    }

    @Override
    public double calcularCustoLocacao(int dias){
        return dias * 80;
    }

}
