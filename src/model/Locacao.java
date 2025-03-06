package model;

import java.util.UUID;
import java.time.LocalDate;
public class Locacao {

    private String id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private double valorTotal;
    private Pagamento pagamento;

    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataRetirada, LocalDate dataDevolucao) {
        this.id = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorTotal = calcularValorTotal();
        this.pagamento = null;
        veiculo.setDisponivel(false);
    }

    // Método para calcular o valor total da locação
    private double calcularValorTotal() {
        int dias = (int) (dataDevolucao.toEpochDay() - dataRetirada.toEpochDay()); //Obtem o dia da data de devolucao e da data de retirada e subtrai para obter a qnt de dias
        return veiculo.calcularCustoLocacao(dias);
    }

    //Método para calcular multa se ocorrer
    public double calcularMulta(LocalDate diaAtual){
        if (diaAtual.isAfter(dataDevolucao)) {
            int diasAtrasados = (int) (diaAtual.toEpochDay() - dataDevolucao.toEpochDay());

            return diasAtrasados * 50.0;
        }
        return 0.0;
    }

    // Método para registrar o pagamento
    public void registrarPagamento(double valorPago, MetodoPagamento metodoPagamento) {
        this.pagamento = new Pagamento(this.id, valorPago, metodoPagamento);
    }

    //Getter PAGAMENTO
    public Pagamento getPagamento() {
        return pagamento;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }
    public Cliente getCliente() {
        return cliente; }
    public Veiculo getVeiculo() {
        return veiculo; }
    public LocalDate getDataRetirada() {
        return dataRetirada; }
    public LocalDate getDataDevolucao() {
        return dataDevolucao; }
    public double getValorTotal() {
        return valorTotal; }


    @Override
    public String toString() {
        return "Locação: " + cliente.getNome() + " | Veículo: " + veiculo.getModelo() + " | Retirada: " + dataRetirada + " | Devolução: " + dataDevolucao + " | Valor: R$ " + valorTotal;
    }
}


