package model;


public class Carro extends Veiculo{

    public Carro(String placa, String modelo, int ano, String tipo) {
        super(placa, modelo, ano, tipo);
    }

    public Carro(int id, String placa, String modelo, int ano, boolean disponivel, String tipo) {
        super(id, placa, modelo, ano, disponivel, tipo);
    }
    @Override
    public double calcularCustoLocacao(int dias){
        return dias * 80;
    }

    @Override
    public String getTipo(){
        return "Carro";
    }

    @Override
    public String toString(){
        return "Placa: " + this.getPlaca() + "\nModelo: " + this.getModelo() + "\nAno: " + this.getAno() + "\nTipo: " + this.getTipo();
    }

}
