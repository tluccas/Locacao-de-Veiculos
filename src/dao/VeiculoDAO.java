package dao;

import model.Veiculo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.VeiculoNaoEncontradoException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private static final String VEICULOS_FILE = "veiculos.json";
    private List<Veiculo> veiculos;

    public VeiculoDAO() throws JsonCarregamentoException {
        Type tipoVeiculo = new TypeToken<List<Veiculo>>(){}.getType();
        veiculos = Persistencia.carregarDados(VEICULOS_FILE, tipoVeiculo);

        if (veiculos == null) {
            veiculos = new ArrayList<>();
        }
    }

    //Método para salvar lista de veículos no arquivo veiculos.json
    private void salvarVeiculos() {
        Persistencia.salvarDados(VEICULOS_FILE, veiculos);
    }

    // Adicionar um novo veículo
    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
        salvarVeiculos();
    }

    // Listar todos os veículos
    public List<Veiculo> listarVeiculos() {
        return veiculos;
    }

    // Buscar veículo por placa
    public Veiculo buscarPorPlaca(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    // Remover veículo por placa
    public void removerVeiculo(String placa) throws VeiculoNaoEncontradoException {
        Veiculo veiculo = buscarPorPlaca(placa);
        if (veiculo == null) {
            throw new VeiculoNaoEncontradoException("Veiculo com placa " + placa + " não encontrado");
        }
        veiculos.remove(veiculo);
        salvarVeiculos();
    }

    public void atualizarVeiculo(Veiculo veiculoAtualizado) throws VeiculoNaoEncontradoException {
        Veiculo veiculoExistente = buscarPorPlaca(veiculoAtualizado.getPlaca());
        if (veiculoExistente == null) {
            throw new VeiculoNaoEncontradoException("Veículo com placa " + veiculoAtualizado.getPlaca() + " não encontrado.");
        }

        // Atualiza os dados do veículo existente
        veiculoExistente.setModelo(veiculoAtualizado.getModelo());
        veiculoExistente.setAno(veiculoAtualizado.getAno());
        veiculoExistente.setDisponivel(veiculoAtualizado.isDisponivel());

        salvarVeiculos(); // Salva a lista atualizada no arquivo JSON
    }
}
