package dao;

import model.Cliente;
import model.Veiculo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.exceptions.ClienteNaoEncontradoException;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.VeiculoNaoEncontradoException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final String CLIENTES_FILE = "clientes.json";
    private List<Cliente> clientes;
    private Gson gson; // Objeto Gson para serialização/desserialização

    public ClienteDAO() throws JsonCarregamentoException {
        gson = new Gson();
        clientes = carregarClientes();
    }

    //Método para salvar os clientes no clientes.json
    private void salvarClientes() {
        try (Writer writer = new FileWriter(CLIENTES_FILE)){ //Cria um obj FileWriter para escrever no clientes.json
            gson.toJson(clientes, writer); //Converte a lista para json e o obj FileWriter escreve no mesmo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Método para carregar os clientes no clientes.json
    private List<Cliente> carregarClientes() throws JsonCarregamentoException {

        try(Reader reader = new FileReader(CLIENTES_FILE)){
            //Criamos um objeto para indicar ao gson que estamos trabalhando com uma lista generica List<Veiculos>
            Type listType = new TypeToken<ArrayList<Cliente>>(){}.getType();
            return gson.fromJson(reader, listType); // Retornamos o objeto leitor e a nossa lista de clientes
        }catch (FileNotFoundException e){
            return new ArrayList<>(); // Retorna a lista vazia se o arquivo não existir
        } catch (IOException e) {
            //Não irá retornar uma lista vazia pois o erro foi outro além do arquivo nao encontrado
            throw new JsonCarregamentoException("Erro ao carregar clientes JSON: " + CLIENTES_FILE, e);
        }
    }

    // Adicionar clientes
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
        salvarClientes();
    }

    // Método para atualizar um cliente
    public void atualizarCliente(Cliente clienteAtualizado) throws ClienteNaoEncontradoException {
        Cliente clienteExistente = buscarClientePorCpf(clienteAtualizado.getCpf()); // Busca o cliente pelo CPF
        if (clienteExistente == null) {
            throw new ClienteNaoEncontradoException("ERRO: Cliente " + clienteAtualizado.getCpf() + " não encontrado");
        }

        // Atualiza os dados do cliente existente
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());

        salvarClientes(); // Salva a lista atualizada no arquivo JSON
    }

    // Listar Clientes
    public List<Cliente> listarClientes() {
        return clientes;
    }

    // Buscar clientes por cpf
    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente c : clientes){
            if (c.getCpf().equals(cpf)){
                return c;
            }
        }
        return null;
    }

    //Remover clientes por cpf
    public void removerCliente(String cpf) throws ClienteNaoEncontradoException {
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null){
            throw new ClienteNaoEncontradoException("ERRO: Cliente " + cpf + " não encontrado");
        }
        clientes.remove(cliente);
        salvarClientes();
    }

}
