package view;

import controller.VeiculoController;
import model.Veiculo;
import model.exceptions.JsonCarregamentoException;

import java.util.List;
import java.util.Scanner;

public class TelaListagem {
    private Scanner scanner;
    private VeiculoController veiculoController;

    public TelaListagem() throws JsonCarregamentoException {
        this.scanner = new Scanner(System.in);
        this.veiculoController = new VeiculoController();
    }

    public void exibirTela() {
        System.out.println("\n--- LISTAGEM DE VEÍCULOS ---");
        System.out.println("1. Veículos Disponíveis");
        System.out.println("2. Veículos Locados");
        System.out.print("Escolha uma opção: ");

        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        List<Veiculo> veiculos = veiculoController.listarVeiculos();
        switch (opcao) {
            case 1:
                System.out.println("\n--- VEÍCULOS DISPONÍVEIS ---");
                veiculos.stream()
                        .filter(Veiculo::isDisponivel) // Filtra veículos disponíveis
                        .forEach(System.out::println); // Exibe cada veículo
                break;
            case 2:
                System.out.println("\n--- VEÍCULOS LOCADOS ---");
                veiculos.stream()
                        .filter(veiculo -> !veiculo.isDisponivel()) // Filtra veículos locados
                        .forEach(System.out::println); // Exibe cada veículo
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
}