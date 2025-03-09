package view;

import controller.ClienteController;
import controller.LocacaoController;
import controller.VeiculoController;
import model.Cliente;
import model.Locacao;
import model.Veiculo;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.LocacaoNaoEncontradaException;
import model.exceptions.VeiculoNaoEncontradoException;

import java.time.LocalDate;
import java.util.Scanner;

public class TelaLocacao {
    private Scanner scanner;
    private LocacaoController locacaoController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;

    public TelaLocacao() throws JsonCarregamentoException {
        this.scanner = new Scanner(System.in);
        this.locacaoController = new LocacaoController();
        this.clienteController = new ClienteController();
        this.veiculoController = new VeiculoController();
    }

    public void exibirTela() throws VeiculoNaoEncontradoException {
        System.out.println("\n--- REGISTRO DE LOCAÇÃO ---");

        // Solicitar CPF do cliente
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();
        Cliente cliente = clienteController.buscarCliente(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        // Solicitar placa do veículo
        System.out.print("Placa do Veículo: ");
        String placa = scanner.nextLine();
        Veiculo veiculo = veiculoController.buscarPorPlaca(placa);
        if (veiculo == null || !veiculo.isDisponivel()) {
            System.out.println("Veículo não encontrado ou indisponível.");
            return;
        }

        // Solicitar datas de retirada e devolução
        System.out.print("Data de Retirada (AAAA-MM-DD): ");
        LocalDate dataRetirada = LocalDate.parse(scanner.nextLine());
        System.out.print("Data de Devolução (AAAA-MM-DD): ");
        LocalDate dataDevolucao = LocalDate.parse(scanner.nextLine());

        // Registrar a locação
        Locacao locacao = new Locacao(cliente, veiculo, dataRetirada, dataDevolucao);
        locacaoController.adicionarLocacao(locacao);

        // Marcar o veículo como indisponível
        veiculo.setDisponivel(false);
        veiculoController.atualizarVeiculo(veiculo);

        System.out.println("Locação registrada com sucesso!");
    }
}