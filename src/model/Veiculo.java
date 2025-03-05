package model;

public abstract class Veiculo {

    private String placa;
    private String modelo;
    private int ano;
    private boolean disponivel;

    public Veiculo(String placa, String modelo, int ano, boolean disponivel) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.disponivel = disponivel;
    }

    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;

    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public boolean isDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        return disponivel;
    }

    public abstract double calcularPrecoLocacao(int preco);
}
