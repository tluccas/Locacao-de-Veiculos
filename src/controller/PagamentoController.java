package controller;

import dao.PagamentoDAO;
import model.Pagamento;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.PagamentoNaoEncontradoException;

import java.util.List;

public class PagamentoController {
    private PagamentoDAO pagamentoDAO;

    public PagamentoController() throws JsonCarregamentoException {

        pagamentoDAO = new PagamentoDAO();
    }

    // Método p/ adicionar um pagamento
    public void adicionarPagamento(Pagamento pagamento) {
        pagamentoDAO.adicionarPagamento(pagamento);
    }

    //Método p/ excluir um pagamento
    public void excluirPagamento(String id) throws PagamentoNaoEncontradoException {
        pagamentoDAO.excluirPagamento(id);
    }

    //Método p/ listar pagamentos
    public List<Pagamento> listarPagamentos() {
        return pagamentoDAO.listarPagamentos();
    }
}
