package dao;

import model.Pagamento;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.PagamentoNaoEncontradoException;


import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    private static final String PAGAMENTOS_FILE = "pagamentos.json";
    private List<Pagamento> pagamentos;

    public PagamentoDAO() throws JsonCarregamentoException {
        // Carrega os pagamentos do arquivo JSON ao inicializar
        Type tipoPagamento = new TypeToken<ArrayList<Pagamento>>() {}.getType();
        pagamentos = Persistencia.carregarDados(PAGAMENTOS_FILE, tipoPagamento);

        if (pagamentos == null) {
            pagamentos = new ArrayList<>(); // Cria uma lista vazia caso o arquivo não seja encontrado
        }
    }

    // Método para salvar os pagamentos no arquivo JSON
    private void salvarPagamento() {
        Persistencia.salvarDados(PAGAMENTOS_FILE, pagamentos);
    }

    //Método para adicionar pagamentos na lista
    public void adicionarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
        salvarPagamento();
    }

    //Método para buscar pagamento por id
    public Pagamento buscarPagamento(String id) throws PagamentoNaoEncontradoException {
        for (Pagamento p : pagamentos) {
            if(p.getId().equals(id)) {
                System.out.println("Pagamento encontrado");
                return p;
            }
        }
        System.out.println("Pagamento nao encontrado");
        return null;
    }

    //Método para remover pagamento da lista
    public void excluirPagamento(String id) throws PagamentoNaoEncontradoException {
        Pagamento pagamentoEncontrado = buscarPagamento(id);
        if (pagamentoEncontrado == null){
            throw new PagamentoNaoEncontradoException("Pagamento nao encontrado");
        }
        pagamentos.remove(pagamentoEncontrado);
        salvarPagamento();
    }

    //Método para listar pagamentos
    public List<Pagamento> listarPagamentos(){
        return pagamentos;
    }

    //Método para calcular o faturamento mensal
    public double faturamentoMensal(){
        LocalDate diaHoje = LocalDate.now(); //Captura a data atual
        int mesHoje = diaHoje.getMonthValue(); // Captura o mes da data atual em formato de inteiro
        int anoHoje = diaHoje.getYear(); // Captura o ano do dia atual em formato de inteiro

        double faturamento = 0.0;
        for (Pagamento p : pagamentos) {
            LocalDate pagamento = p.getDataPagamento();

            if (pagamento.getMonthValue() == mesHoje && pagamento.getYear() == anoHoje) {
                faturamento += p.getValorPago();
            }
        }
        return faturamento;
    }

}
