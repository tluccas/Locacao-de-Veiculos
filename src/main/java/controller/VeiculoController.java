package controller;

import dao.VeiculoDAO;
import model.Veiculo;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.VeiculoNaoEncontradoException;

import java.sql.SQLException;
import java.util.List;

public class VeiculoController {
    private VeiculoDAO veiculoDAO;

    public VeiculoController() throws JsonCarregamentoException {
        this.veiculoDAO = new VeiculoDAO();
    }

    // Método para adicionar um veículo
    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo.");
        }
        veiculoDAO.adicionarVeiculo(veiculo);
    }

    // Método para listar todos os veículos
    public List<Veiculo> listarVeiculos() {
        return veiculoDAO.listarVeiculos();
    }

    // Método para buscar um veículo por placa
    public Veiculo buscarPorPlaca(String placa) throws VeiculoNaoEncontradoException {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia.");
        }
        return veiculoDAO.buscarPorPlaca(placa);
    }

    public void atualizarVeiculo(Veiculo veiculo) throws VeiculoNaoEncontradoException, SQLException {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo.");
        }
        veiculoDAO.atualizarVeiculo(veiculo);
    }

    public void atualizarDisponibilidade(Veiculo veiculo, boolean disponivel) throws VeiculoNaoEncontradoException, SQLException {
        if (veiculo != null) {
            veiculo.setDisponivel(disponivel);
            veiculoDAO.atualizarVeiculo(veiculo);
        }
    }
}