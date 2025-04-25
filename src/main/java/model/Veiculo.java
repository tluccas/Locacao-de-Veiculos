package model;

import java.util.UUID;
public abstract class Veiculo {
    private static int ultimoId = 0;
    private int id;
    private String placa;
    private String modelo;
    private int ano;
    private boolean disponivel;
    private String tipo;

    public Veiculo(String placa, String modelo, int ano, String tipo) {
        this.id = gerarId();
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.disponivel = true;
        this.tipo = tipo;
    }

    public int gerarId(){
        ultimoId++;
        return ultimoId;
    }
    public int getId() {
        return id;
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
    public abstract String getTipo(); //Para verificar o tipo de veiculo Carro, Caminhão ou Moto

    public abstract double calcularCustoLocacao(int preco);

    public abstract String toString();
}
