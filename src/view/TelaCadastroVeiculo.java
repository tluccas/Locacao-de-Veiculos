package view;

import controller.VeiculoController;
import model.Caminhao;
import model.Carro;
import model.Moto;
import model.Veiculo;
import model.exceptions.JsonCarregamentoException;

import java.util.Scanner;

public class TelaCadastroVeiculo {
    private Scanner scanner;
    private VeiculoController veiculoController;

    public TelaCadastroVeiculo() throws JsonCarregamentoException {
        this.scanner = new Scanner(System.in);
        this.veiculoController = new VeiculoController();
    }

    public void exibirTela() {
        System.out.println("\n--- CADASTRO DE VEÍCULO ---");
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("Selecione o tipo de veículo:");
        System.out.println("1. Carro");
        System.out.println("2. Moto");
        System.out.println("3. Caminhão");
        System.out.print("Opção: ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Veiculo veiculo = null;
        switch (tipo) {
            case 1:
                veiculo = new Carro(placa, modelo, ano);
                break;
            case 2:
                veiculo = new Moto(placa, modelo, ano);
                break;
            case 3:
                veiculo = new Caminhao(placa, modelo, ano);
                break;
            default:
                System.out.println("Tipo de veículo inválido.");
                return;
        }

        veiculoController.adicionarVeiculo(veiculo);
        System.out.println("Veículo cadastrado com sucesso!");
    }
}