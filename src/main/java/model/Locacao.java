package model;

import java.util.UUID;
import java.time.LocalDate;
public class Locacao {

    private String status;
    private String id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private double valorTotal;
    private Pagamento pagamento;

    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataRetirada, LocalDate dataDevolucao) {
        //Garante que a data de devolução seja menor que a de retirada para evitar erros
        if (dataDevolucao.isBefore(dataRetirada)) {
            throw new IllegalArgumentException("A data de devolução não pode ser antes da data de retirada.");
        }

        this.id = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorTotal = calcularValorTotal(LocalDate.now());
        this.pagamento = null;
        this.status = "Ativo";
        veiculo.setDisponivel(false);
    }

    // Método para calcular o valor total da locação
    public double calcularValorTotal(LocalDate diaAtual) {
        //Obtem o dia da data de devolucao e da data de retirada e subtrai para obter a qnt de dias
        int dias = (int) (dataDevolucao.toEpochDay() - dataRetirada.toEpochDay());

        double multa = 0.0;
        //Calcula multa se acontecer
        if (diaAtual.isAfter(dataDevolucao)) {
            int diasAtrasados = (int) (diaAtual.toEpochDay() - dataDevolucao.toEpochDay());

            multa = diasAtrasados * 50.0;
        }

        return veiculo.calcularCustoLocacao(dias) + multa; //Retorna o custo da locacao + multa se tiver
    }


    // Método para registrar o pagamento
    public void registrarPagamento(double valorPago, LocalDate dataDevolucao, MetodoPagamento metodoPagamento) {
        this.pagamento = new Pagamento(this.id, valorPago, dataDevolucao, metodoPagamento);
    }

    //Método para definir status da locação
    public void atualizarStatus(){
        LocalDate dataAtual = LocalDate.now();

        if (dataAtual.isBefore(dataDevolucao) || dataAtual.isEqual(dataDevolucao)) {
            this.status = "Ativo";
        }else if(pagamento != null && valorTotal >= pagamento.getValorPago()){
            this.status = "Finalizado";
        }else{
            this.status = "Atrasado";
        }
    }

    // Getters e Setters
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }
    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public String getId() {
        return id;
    }
    public Cliente getCliente() {
        return cliente; }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo; }
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada; }
    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public double getValorTotal() {
        return valorTotal; }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }


    @Override
    public String toString() {
        return "Locação: " + cliente.getNome() + " | Veículo: " + veiculo.getModelo() + " | Retirada: " + dataRetirada + " | Devolução: " + dataDevolucao + " | Valor: R$ " + valorTotal;
    }
}


