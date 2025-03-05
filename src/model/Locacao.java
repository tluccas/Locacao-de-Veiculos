package model;

import java.time.LocalDate;
public class Locacao {

    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private double valorTotal;

    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataRetirada, LocalDate dataDevolucao) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorTotal = calcularValorTotal();
        veiculo.isDisponivel(false);
    }

    // Método para calcular o valor total da locação
    private double calcularValorTotal() {
        int dias = (int) (dataDevolucao.toEpochDay() - dataRetirada.toEpochDay());
        return veiculo.calcularPrecoLocacao(dias);
    }

    // Getters e Setters
    public Cliente getCliente() { return cliente; }
    public Veiculo getVeiculo() { return veiculo; }
    public LocalDate getDataRetirada() { return dataRetirada; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public double getValorTotal() { return valorTotal; }

    @Override
    public String toString() {
        return "Locação: " + cliente.getNome() + " | Veículo: " + veiculo.getModelo() + " | Retirada: " + dataRetirada + " | Devolução: " + dataDevolucao + " | Valor: R$ " + valorTotal;
    }
}

}
