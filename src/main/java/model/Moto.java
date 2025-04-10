package model;

public class Moto extends Veiculo{

    public Moto(String placa, String modelo, int ano, String tipo){
        super(placa, modelo, ano, tipo);
    }

    @Override
    public double calcularCustoLocacao(int dias){
        return dias * 50;
    }

    @Override
    public String getTipo(){
        return "Moto";
    }

    @Override
    public String toString(){
        return "Placa: " + this.getPlaca() + "\nModelo: " + this.getModelo() + "\nAno: " + this.getAno() + "\nTipo: " + this.getTipo();
    }
}
