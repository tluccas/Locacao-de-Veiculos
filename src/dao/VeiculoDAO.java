package dao;

import model.Veiculo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private static final String FILE_PATH = "veiculos.json";
    private List<Veiculo> veiculos;
    private Gson gson;

    public VeiculoDAO() {
        gson = new Gson();
        veiculos = carregarVeiculos();
    }

    // Salvar lista de veículos no arquivo JSON
    private void salvarVeiculos() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(veiculos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carregar veículos do JSON
    private List<Veiculo> carregarVeiculos() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Veiculo>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Retorna lista vazia se o arquivo não existir
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
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
    public boolean removerVeiculo(String placa) {
        Veiculo veiculo = buscarPorPlaca(placa);
        if (veiculo != null) {
            veiculos.remove(veiculo);
            salvarVeiculos();
            return true;
        }
        return false;
    }
}
