package model;

public class Caminhao extends Veiculo{

    public Caminhao(String placa, String modelo, int ano, String tipo){
        super(placa, modelo, ano, tipo);
    }

    public Caminhao(int id, String placa, String modelo, int ano, boolean veiculoDispinivel, String tipo){
        super(id, placa, modelo, ano, veiculoDispinivel, tipo);
    }
    @Override
    public double calcularCustoLocacao(int dias){
        return dias * 100;
    }

    @Override
    public String getTipo(){
        return "Caminhão";
    }

    @Override
    public String toString(){
        return "Placa: " + this.getPlaca() + "\nModelo: " + this.getModelo() + "\nAno: " + this.getAno() + "\nTipo: " + this.getTipo();
    }
}
