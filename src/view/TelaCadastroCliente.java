package view;

import controller.ClienteController;
import model.Cliente;
import model.exceptions.JsonCarregamentoException;

import java.util.Scanner;

public class TelaCadastroCliente {
    private Scanner scanner;
    private ClienteController clienteController;

    public TelaCadastroCliente() throws JsonCarregamentoException {
        this.scanner = new Scanner(System.in);
        this.clienteController = new ClienteController();
    }

    public void exibirTela() {
        System.out.println("\n--- CADASTRO DE CLIENTE ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        clienteController.adicionarCliente(nome, cpf, email);
        System.out.println("Cliente cadastrado com sucesso!");
    }
}