package dao;

import model.Cliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.exceptions.ClienteNaoEncontradoException;
import model.exceptions.JsonCarregamentoException;
import java.sql.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final String CLIENTES_FILE = "clientes.json";
    private List<Cliente> clientes;

    public ClienteDAO() throws JsonCarregamentoException {
        Type tipoCliente = new TypeToken<ArrayList<Cliente>>(){}.getType();
        clientes = Persistencia.carregarDados(CLIENTES_FILE, tipoCliente);

        if (clientes == null){
            clientes = new ArrayList<>();
        }
    }

    //Método para salvar os clientes no clientes.json
    public void salvarClientes() {
        Persistencia.salvarDados(CLIENTES_FILE, clientes); // recebe o caminho do arquivo e o tipo de objeto
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
            throw new ClienteNaoEncontradoException("ERRO: Cliente não encontrado");
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
        System.out.println("CLIENTE NÃO ENCONTRADO");
        return null;
    }

    //Remover clientes por cpf
    public void removerCliente(String cpf) throws ClienteNaoEncontradoException {
        Cliente clienteExistente = buscarClientePorCpf(cpf);
        if (clienteExistente == null){
            throw new ClienteNaoEncontradoException("ERRO: Cliente " + cpf + " não encontrado");
        }
        clientes.remove(clienteExistente);
        salvarClientes();
    }

}
