package controller;

import dao.LocacaoDAO;
import model.Cliente;
import model.Locacao;
import model.Pagamento;
import model.Veiculo;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.LocacaoNaoEncontradaException;

import java.time.LocalDate;
import java.util.List;

public class LocacaoController {
    private LocacaoDAO locacaoDAO;

    public LocacaoController() throws JsonCarregamentoException {
        locacaoDAO = new LocacaoDAO();
    }

    // Método p/ adicionar locação
    public void adicionarLocacao(Cliente cliente, Veiculo veiculo, LocalDate dataRetirada, LocalDate dataDevolucao) {
        if (cliente == null || veiculo == null || dataRetirada == null || dataDevolucao == null) {
            throw new IllegalArgumentException("Erro: Todos os campos devem ser preenchidos.");
        }

        Locacao locacao = new Locacao(cliente, veiculo, dataRetirada, dataDevolucao);
        locacaoDAO.adicionarLocacao(locacao);
    }

    //Método p/ atualizar uma locação
    public void atualizarLocacao(String id, LocalDate dataDevolucao, String status, double valorTotal, Pagamento pagamento) {
            Locacao locacaoExistente = locacaoDAO.buscarLocacoes(id);
            if (locacaoExistente == null) {
                throw new LocacaoNaoEncontradaException("Erro: locção não encontrada");
            }

            locacaoExistente.setDataDevolucao(dataDevolucao);
            locacaoExistente.setStatus(status);
            locacaoExistente.setValorTotal(valorTotal);
            locacaoExistente.setPagamento(pagamento);
            locacaoDAO.atualizarLocacao(locacaoExistente);

    }

    // Método p/ listar locações
    public List<Locacao> listarLocacoes() {
        return locacaoDAO.listarLocacoes();
    }

    // Método p/ buscar locação por id
    public Locacao buscarLocacao(String id) {
        return locacaoDAO.buscarLocacoes(id);
    }

    // Método p/ remover uma locação pelo id
    public void removerLocacao(String id) {
        locacaoDAO.excluirLocacao(id);
    }

}
