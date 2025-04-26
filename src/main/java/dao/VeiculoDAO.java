package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConfig;
import db.DBConnection;
import model.*;
import model.exceptions.VeiculoNaoEncontradoException;

public class VeiculoDAO {

    String DB_URL = DatabaseConfig.getURL();
    String DB_USER = DatabaseConfig.getUser();
    String DB_PASSWORD = DatabaseConfig.getPassword();

    public VeiculoDAO() {
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        String sql = "INSERT INTO veiculos (placa, modelo, ano, disponivel, tipo) VALUES (?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3,veiculo.getAno());
            stmt.setBoolean(4, veiculo.isDisponivel());
            stmt.setString(5, veiculo.getTipo());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Veiculo adicionado com sucesso!");
            }else{
                System.out.println("Erro ao adicionar veiculo!");
            }
        }catch (SQLException e) {
            System.err.println("Erro ao adicionar veiculo!" + e.getMessage());
            e.printStackTrace();
        }
    }

    public Veiculo buscarPorPlaca(String placa) throws VeiculoNaoEncontradoException {
        String sql = "SELECT * FROM veiculos WHERE placa = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, placa);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                placa = rs.getString("placa");
                String modelo = rs.getString("modelo");
                int ano = rs.getInt("ano");
                boolean disponivel = rs.getBoolean("disponivel");
                String tipo = rs.getString("tipo");

                return criarVeiculo(id, placa, modelo, ano, disponivel, tipo);
            }else{
                throw new VeiculoNaoEncontradoException("Veiculo não encontrado ou inexistente");
            }
        }catch (SQLException e){
            System.err.println("Erro ao buscar veiculo!" + e.getMessage());
            e.printStackTrace();
        }
            return null;
    }

    public List<Veiculo> listarVeiculos() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String placa = rs.getString("placa");
                String modelo = rs.getString("modelo");
                int ano = rs.getInt("ano");
                boolean disponivel = rs.getBoolean("disponivel");
                String tipo = rs.getString("tipo");

                veiculos.add(criarVeiculo(id, placa, modelo, ano, disponivel, tipo));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veiculos;
    }

    public void atualizarVeiculo(Veiculo veiculoAtualizado) throws VeiculoNaoEncontradoException, SQLException {
        String sql = "UPDATE veiculos SET modelo = ?, ano = ?, disponivel = ?, tipo = ? WHERE placa = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Verifica se o veículo existe
            if (buscarPorPlaca(veiculoAtualizado.getPlaca()) == null) {
                throw new VeiculoNaoEncontradoException("Veículo com placa " + veiculoAtualizado.getPlaca() + " não encontrado.");
            }

            // Preenche os parâmetros da query
            stmt.setString(1, veiculoAtualizado.getModelo());
            stmt.setInt(2, veiculoAtualizado.getAno());
            stmt.setBoolean(3, veiculoAtualizado.isDisponivel());
            stmt.setString(4, veiculoAtualizado.getTipo());
            stmt.setString(5, veiculoAtualizado.getPlaca());

            // Executa a atualização
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new VeiculoNaoEncontradoException("Nenhum veículo foi atualizado.");
            }
        }
    }


    public Veiculo criarVeiculo(int id, String placa, String modelo, int ano, boolean disponivel, String tipo) {
        boolean veiculoDisponivel = (id == 0) ? true : disponivel;
        switch (tipo){
            case "Carro":
                return new Carro(id, placa, modelo, ano, veiculoDisponivel, tipo);

            case "Moto":
                return new Moto(id, placa, modelo, ano, veiculoDisponivel, tipo);
            case "Caminhão":
                return new Caminhao(id, placa, modelo, ano, veiculoDisponivel, tipo);
            default:
                throw new IllegalStateException("Tipo de veículo inválido: " + tipo);
        }
    }
}