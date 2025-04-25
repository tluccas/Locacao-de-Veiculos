package model;

import java.time.LocalDate;
public class Pagamento {
    private static int ultimoId = 0;
    private int id;
    private int Locacaoid;
    private double valorPago;
    private LocalDate dataPagamento;
    private MetodoPagamento metodoPagamento;

    public Pagamento(int Locacaoid, double valorPago, LocalDate dataPagamento, MetodoPagamento metodoPagamento) {
        this.id = getId();
        this.Locacaoid = Locacaoid;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.metodoPagamento = metodoPagamento;
    }

    public int ultimoId() {
        ultimoId++;
        return ultimoId;
    }
    //Getters e Setters

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getLocacaoid() {
        return Locacaoid;
    }
    public void setLocacaoid(int locacaoid) {
        Locacaoid = locacaoid;
    }
    public double getValorPago() {
        return valorPago;
    }
    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }
    public LocalDate getDataPagamento() {
        return dataPagamento;
    }
    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    // Método toString para exibir detalhes do pagamento
    @Override
    public String toString() {
        return "Pagamento [ID: " + id + ", ID Locação: " + Locacaoid + ", Valor Pago: R$ " + valorPago
                + ", Data: " + dataPagamento;
    }
}
