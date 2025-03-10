package view;

import controller.LoginController;
import controller.UsuarioController;
import dao.ClienteDAO;
import dao.LocacaoDAO;
import dao.VeiculoDAO;
import model.Usuario;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.SenhaOuUserIncorretoException;
import model.exceptions.UsuarioNaoEncontradoException;
import model.exceptions.VeiculoNaoEncontradoException;

import java.util.Scanner;

public class TelaPrincipal {
    private Scanner scanner;
    private LoginController loginController;
    private UsuarioController usuarioController;

    public TelaPrincipal() throws JsonCarregamentoException {
        this.scanner = new Scanner(System.in);
        this.loginController = new LoginController();
        this.usuarioController = new UsuarioController();
    }

    public void exibirTela() {
        while (true) {
            System.out.println("\n--- TELA PRINCIPAL ---");
            System.out.println("1. Fazer Login");
            System.out.println("0. Sair do Programa");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    fazerLogin();
                    break;
                case "0":
                    System.out.println("Salvando dados e saindo do programa...");
                    salvarDados(); // Salva todos os dados em JSON antes de sair
                    System.exit(0); // Encerra o programa
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void fazerLogin() {
        System.out.print("Usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            Usuario user = loginController.fazerLogin(usuario, senha);
            redirecionarUsuario(user);
        } catch (UsuarioNaoEncontradoException | SenhaOuUserIncorretoException | JsonCarregamentoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void redirecionarUsuario(Usuario usuario) throws JsonCarregamentoException {
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

    private void exibirMenuAdministrador() throws JsonCarregamentoException {
        String controle;
        do {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("0. Voltar para o Login");
            System.out.print("Escolha uma opção: ");
            controle = scanner.nextLine();

            switch (controle) {
                case "1":
                    TelaCadastroUsuario telaCadastroUsuario = new TelaCadastroUsuario();
                    telaCadastroUsuario.exibirTela();
                    break;
                case "0":
                    System.out.println("Voltando para o login...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (!controle.equals("0"));
    }



    private void exibirMenuGerente() throws JsonCarregamentoException {
        String controle;
        do {
            System.out.println("\n--- MENU GERENTE ---");
            System.out.println("1. Cadastrar Veículo");
            System.out.println("2. Listar Veículos");
            System.out.println("3. Cadastrar Cliente");
            System.out.println("4. Visualizar Relatórios");
            System.out.println("0. Voltar para o Login");
            System.out.print("Escolha uma opção: ");
            controle = scanner.nextLine();

            switch (controle) {
                case "1":
                    try {
                        TelaCadastroVeiculo telaCadastroVeiculo = new TelaCadastroVeiculo();
                        telaCadastroVeiculo.exibirTela();
                    } catch (JsonCarregamentoException e) {
                        System.out.println("Erro ao carregar dados: " + e.getMessage());
                    }
                    break;
                case "2":
                    try {
                        TelaListagem telaListagem = new TelaListagem();
                        telaListagem.exibirTela();
                    } catch (JsonCarregamentoException e) {
                        System.out.println("Erro ao carregar dados: " + e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        TelaCadastroCliente telaCadastroCliente = new TelaCadastroCliente();
                        telaCadastroCliente.exibirTela();
                    } catch (JsonCarregamentoException e) {
                        System.out.println("Erro ao carregar dados: " + e.getMessage());
                    }
                    break;
                case "4":
                    try {
                        TelaPagamentos telaPagamentos = new TelaPagamentos();
                        telaPagamentos.exibirTela();
                    } catch (JsonCarregamentoException e) {
                        System.out.println("Erro ao carregar dados: " + e.getMessage());
                    }
                    break;
                case "0":
                    System.out.println("Voltando para o login...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (!controle.equals("0"));
    }

    private void exibirMenuAtendente() {
        String controle;
        do {
            System.out.println("\n--- MENU ATENDENTE ---");
            System.out.println("1. Registrar Locação");
            System.out.println("2. Registrar Devolução");
            System.out.println("0. Voltar para o Login");
            System.out.print("Escolha uma opção: ");
            controle = scanner.nextLine();

            switch (controle) {
                case "1":
                    try {
                        TelaLocacao telaLocacao = new TelaLocacao();
                        telaLocacao.exibirTela();
                    } catch (JsonCarregamentoException | VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case "2":
                    try {
                        TelaDevolucao telaDevolucao = new TelaDevolucao();
                        telaDevolucao.exibirTela();
                    } catch (JsonCarregamentoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case "0":
                    System.out.println("Voltando para o login...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (!controle.equals("0"));
    }

    private void salvarDados() {
        try {
            // Salva dados de locações
            LocacaoDAO locacaoDAO = new LocacaoDAO();
            locacaoDAO.salvarLocacao();

            // Salva dados de veículos
            VeiculoDAO veiculoDAO = new VeiculoDAO();
            veiculoDAO.salvarVeiculos();

            // Salva dados de clientes
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.salvarClientes();

            System.out.println("Todos os dados foram salvos com sucesso!");
        } catch (JsonCarregamentoException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}