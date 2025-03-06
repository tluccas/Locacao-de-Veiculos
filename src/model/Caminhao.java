package model;

public class Caminhao extends Veiculo{

    public Caminhao(String placa, String modelo, int ano){
        super(placa, modelo, ano);
    }

    @Override
    public double calcularCustoLocacao(int dias){
        return dias * 100;
    }

}
