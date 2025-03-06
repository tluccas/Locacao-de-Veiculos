package model;

import java.util.UUID;
public abstract class Veiculo {
    private String id;
    private String placa;
    private String modelo;
    private int ano;
    private boolean disponivel;

    public Veiculo(String placa, String modelo, int ano) {
        this.id = UUID.randomUUID().toString();
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.disponivel = true;
    }

    public String getId() {
        return "ID DO VEICULO: " + id;
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
    public boolean isDisponivel() {
        return disponivel;
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public abstract double calcularCustoLocacao(int preco);
}
