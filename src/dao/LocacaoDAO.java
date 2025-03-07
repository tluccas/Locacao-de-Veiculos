package dao;

import model.Locacao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.LocacaoNaoEncontradaException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocacaoDAO {

    private static final String LOCACAO_FILE = "locacao.json";
    private List<Locacao> locacoes;

    public LocacaoDAO() throws JsonCarregamentoException {
        Type tipoLocacao = new TypeToken<Locacao>() {}.getType();
        locacoes = Persistencia.carregarDados(LOCACAO_FILE, tipoLocacao);

        if (locacoes == null){
            locacoes = new ArrayList<>(); //Cria uma lista vazia caso o arquivo nao seja encontrado
        }
    }

    //Método para salvar as locacoes no json
    private void salvarLocacao() {
        Persistencia.salvarDados(LOCACAO_FILE, locacoes);
    }


    // Método p/ buscar locação por id
    public Locacao buscarLocacoes(String id){
        for (Locacao l : locacoes) {
            if (l.getId().equals(id)) {
                System.out.println("Locacao encontrada");
                return l;
            }

        }
        System.out.println("Locacao nao encontrada");
        return null;
    }

    //Método p/ adicionar locação na lista
    public void adicionarLocacao(Locacao locacao) {
        locacoes.add(locacao);
        salvarLocacao();
    }

    //Método p/ atualizar locação
    public void atualizarLocacao(Locacao locacaoAtualizada) {
        Locacao locacaoExistente = buscarLocacoes(locacaoAtualizada.getId());
        if (locacaoExistente == null) {
            throw new LocacaoNaoEncontradaException("Locacao nao encontrada");
        }

        locacaoExistente.setDataDevolucao(locacaoAtualizada.getDataDevolucao()); //Atualiza a data de devolução
        locacaoExistente.setStatus(locacaoAtualizada.getStatus()); // Atualiza o status
        locacaoExistente.setValorTotal(locacaoAtualizada.getValorTotal()); // Atualiza o valor total
        locacaoExistente.setPagamento(locacaoAtualizada.getPagamento()); // Atualiza o pagamento

        salvarLocacao();
    }

    //Método p/ remover locação
    public void excluirLocacao(String id) throws LocacaoNaoEncontradaException {
        Locacao locacaoExistente = buscarLocacoes(id);
        if (locacaoExistente == null) {
            throw new LocacaoNaoEncontradaException("Locacao nao encontrada");
        }
        locacoes.remove(locacaoExistente);
    }
}

