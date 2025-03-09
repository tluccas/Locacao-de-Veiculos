package view;

import controller.LoginController;
import model.Usuario;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.SenhaOuUserIncorretoException;
import model.exceptions.UsuarioNaoEncontradoException;

import java.util.Scanner;

public class TelaPrincipal {
    private Scanner scanner;
    private LoginController loginController;

    public TelaPrincipal() throws JsonCarregamentoException {
        this.scanner = new Scanner(System.in);
        this.loginController = new LoginController();
    }

    public void exibirTela() {
        while (true) {
            System.out.println("\n--- TELA PRINCIPAL ---");
            System.out.print("Usuário: ");
            String usuario = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            try {
                Usuario user = loginController.fazerLogin(usuario, senha);
                redirecionarUsuario(user);
                break; // Sai do loop após login bem-sucedido
            } catch (UsuarioNaoEncontradoException | SenhaOuUserIncorretoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void redirecionarUsuario(Usuario usuario) {
        switch (usuario.getClass().getSimpleName()) {
            case "Administrador":
                exibirMenuAdministrador();
                break;
            case "Gerente":
                exibirMenuGerente();
                break;
            case "Atendente":
                exibirMenuAtendente();
                break;
            default:
                System.out.println("Tipo de usuário desconhecido.");
        }
    }

    private void exibirMenuAdministrador() {
        System.out.println("\n--- MENU ADMINISTRADOR ---");
        System.out.println("1. Cadastrar Usuário");
        System.out.println("0. Sair");
        // Implementar funcionalidades do administrador
    }

    private void exibirMenuGerente() {
        System.out.println("\n--- MENU GERENTE ---");
        System.out.println("1. Cadastrar Veículo");
        System.out.println("2. Cadastrar Cliente");
        System.out.println("3. Visualizar Relatórios");
        System.out.println("0. Sair");
        // Implementar funcionalidades do gerente
    }

    private void exibirMenuAtendente() {
        System.out.println("\n--- MENU ATENDENTE ---");
        System.out.println("1. Registrar Locação");
        System.out.println("2. Registrar Devolução");
        System.out.println("0. Sair");
        // Implementar funcionalidades do atendente
    }
}