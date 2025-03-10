package dao;

import model.Locacao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Pagamento;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.LocacaoNaoEncontradaException;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocacaoDAO {

    private static final String LOCACAO_FILE = "locacao.json";
    private List<Locacao> locacoes;

    public LocacaoDAO() throws JsonCarregamentoException {
        Type tipoLocacao = new TypeToken<List<Locacao>>() {}.getType();
        locacoes = Persistencia.carregarDados(LOCACAO_FILE, tipoLocacao);

        if (locacoes == null){
            locacoes = new ArrayList<>(); //Cria uma lista vazia caso o arquivo nao seja encontrado
        }
    }

    //Método para salvar as locacoes no json
    public void salvarLocacao() {
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
    public void atualizarLocacao(Locacao locacaoAtualizada) throws LocacaoNaoEncontradaException {
        Locacao locacaoExistente = buscarLocacoes(locacaoAtualizada.getId());
        if (locacaoExistente == null) {
            throw new LocacaoNaoEncontradaException("Locação não encontrada.");
        }

        // Atualiza os dados da locação existente
        locacaoExistente.setCliente(locacaoAtualizada.getCliente());
        locacaoExistente.setVeiculo(locacaoAtualizada.getVeiculo());
        locacaoExistente.setDataRetirada(locacaoAtualizada.getDataRetirada());
        locacaoExistente.setDataDevolucao(locacaoAtualizada.getDataDevolucao());
        locacaoExistente.setValorTotal(locacaoAtualizada.getValorTotal());
        locacaoExistente.setPagamento(locacaoAtualizada.getPagamento());

        salvarLocacao(); // Salva a lista atualizada no arquivo JSON
    }

    //Método p/ listar locações
    public List<Locacao> listarLocacoes(){
        return locacoes;
    }

    //Método p/ remover locação
    public void excluirLocacao(String id) throws LocacaoNaoEncontradaException {
        Locacao locacaoExistente = buscarLocacoes(id);
        if (locacaoExistente == null) {
            throw new LocacaoNaoEncontradaException("Locacao nao encontrada");
        }
        locacoes.remove(locacaoExistente);
    }

    //Buscar locaçao por veiculo
    public Locacao buscarLocacaoPorVeiculo(String placa) {
        for (Locacao locacao : locacoes) {
            if (locacao.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {
                return locacao;
            }
        }
        return null; // Retorna null se não encontrar uma locação para o veículo
    }
}

