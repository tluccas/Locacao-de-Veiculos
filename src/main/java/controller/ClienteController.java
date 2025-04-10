package controller;

import dao.ClienteDAO;
import model.Cliente;
import model.exceptions.ClienteNaoEncontradoException;
import model.exceptions.JsonCarregamentoException;
import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() throws JsonCarregamentoException {
        clienteDAO = new ClienteDAO();
    }

    // Método p/ adicionar um cliente
    public void adicionarCliente(String nome, String cpf, String email){
        if(nome.isEmpty() || cpf.isEmpty() || email.isEmpty()){
            throw new IllegalArgumentException("Erro: algum campo está vazio");
        }

        Cliente cliente = new Cliente(nome, cpf, email);
        clienteDAO.adicionarCliente(cliente);
    }

    // Método p/ atualizar um cliente
    public void atualizarCliente(String nome, String cpf, String email) throws ClienteNaoEncontradoException{
        Cliente clienteExistente = clienteDAO.buscarClientePorCpf(cpf);
        if(clienteExistente == null){
            throw new ClienteNaoEncontradoException("Erro cliente não encontrado");
        }

        clienteExistente.setNome(nome);
        clienteExistente.setEmail(email);

        clienteDAO.atualizarCliente(clienteExistente);
    }

    // Método p/ listar todos os clientes
    public List<Cliente> listarClientes(){
        return clienteDAO.listarClientes();
    }

    // Método p/ remover um cliente por cpf
    public void excluirCliente(String cpf) throws ClienteNaoEncontradoException{
        clienteDAO.removerCliente(cpf);
    }

    //Método p/ buscar clientes por cpf
    public Cliente buscarCliente(String cpf) throws ClienteNaoEncontradoException{
        return clienteDAO.buscarClientePorCpf(cpf);
    }
}
