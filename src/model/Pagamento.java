package model;

import java.util.UUID;
import java.time.LocalDate;
public class Pagamento {

    private String id;
    private String Locacaoid;
    private double valorPago;
    private LocalDate dataPagamento;
    private MetodoPagamento metodoPagamento;

    public Pagamento(String Locacaoid, double valorPago, LocalDate dataPagamento, MetodoPagamento metodoPagamento) {
        this.id = UUID.randomUUID().toString();
        this.Locacaoid = Locacaoid;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.metodoPagamento = metodoPagamento;
    }


    //Getters e Setters

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLocacaoid() {
        return Locacaoid;
    }
    public void setLocacaoid(String locacaoid) {
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
