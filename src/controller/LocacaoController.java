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
    public void adicionarLocacao(Locacao locacao) {
        if (locacao == null || locacao.getCliente() == null || locacao.getVeiculo() == null ||
                locacao.getDataRetirada() == null || locacao.getDataDevolucao() == null) {
            throw new IllegalArgumentException("Erro: Todos os campos da locação devem ser preenchidos.");
        }
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

    //Método buscar locação por veiculo
    public Locacao buscarLocacaoPorVeiculo(String placa) throws LocacaoNaoEncontradaException {
        Locacao locacao = locacaoDAO.buscarLocacaoPorVeiculo(placa);
        if (locacao == null) {
            throw new LocacaoNaoEncontradaException("Locação não encontrada para o veículo com placa " + placa);
        }
        return locacao;
    }
}
