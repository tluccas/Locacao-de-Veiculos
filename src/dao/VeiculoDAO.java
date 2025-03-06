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
    private Gson gson;

    public VeiculoDAO() throws JsonCarregamentoException {
        gson = new Gson();
        veiculos = carregarVeiculos();
    }

    //Método para salvar lista de veículos no arquivo veiculos.json
    private void salvarVeiculos() {
        try (Writer writer = new FileWriter(VEICULOS_FILE)) {
            gson.toJson(veiculos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Método para carregar veículos do veiculos.json
    private List<Veiculo> carregarVeiculos() throws JsonCarregamentoException {
        try (Reader reader = new FileReader(VEICULOS_FILE)) {
            //Criamos um objeto para indicar ao gson que estamos trabalhando com uma lista generica List<Veiculos>
            Type listType = new TypeToken<ArrayList<Veiculo>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Retorna lista vazia se o arquivo não existir
        } catch (IOException e) {
            //Não irá criar uma lista vazia pois o erro foi outro além do arquivo nao encontrado
            throw new JsonCarregamentoException("Erro ao carregar veiculos JSON: " + VEICULOS_FILE, e);
        }
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
}
