package view;

import controller.LocacaoController;
import controller.PagamentoController;
import controller.VeiculoController;
import model.Locacao;
import model.Pagamento;
import model.Veiculo;
import model.MetodoPagamento;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.LocacaoNaoEncontradaException;
import model.exceptions.VeiculoNaoEncontradoException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TelaDevolucao {
    private Scanner scanner;
    private LocacaoController locacaoController;
    private VeiculoController veiculoController;
    private PagamentoController pagamentoController;

    public TelaDevolucao() throws JsonCarregamentoException {
        this.scanner = new Scanner(System.in);
        this.locacaoController = new LocacaoController();
        this.veiculoController = new VeiculoController();
        this.pagamentoController = new PagamentoController();
    }

    public void exibirTela() {
        System.out.println("\n--- REGISTRO DE DEVOLUÇÃO ---");

        // Solicitar placa do veículo
        System.out.print("Placa do Veículo: ");
        String placa = scanner.nextLine();

        try {
            // Buscar o veículo pela placa
            Veiculo veiculo = veiculoController.buscarPorPlaca(placa);
            if (veiculo == null) {
                System.out.println("Veículo não encontrado.");
                return;
            }

            // Verificar se o veículo já está disponível (já foi devolvido)
            if (veiculo.isDisponivel()) {
                System.out.println("Este veículo já está disponível. Nenhuma locação ativa encontrada.");
                return;
            }

            // Buscar a locação ativa associada ao veículo
            Locacao locacao = locacaoController.buscarLocacaoPorVeiculo(placa);
            if (locacao == null) {
                System.out.println("Locação não encontrada para o veículo com placa " + placa);
                return;
            }

            // Solicitar data de devolução real
            LocalDate dataDevolucaoReal = null;
            boolean dataValida = false;
            while (!dataValida) {
                System.out.print("Data de Devolução Real (AAAA-MM-DD): ");
                String dataDevolucaoStr = scanner.nextLine();
                try {
                    dataDevolucaoReal = LocalDate.parse(dataDevolucaoStr);
                    dataValida = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de data inválido. Use o formato AAAA-MM-DD.");
                }
            }

            // Calcular valor total da locação (incluindo multa por atraso, se houver)
            double valorTotal = locacao.calcularValorTotal(dataDevolucaoReal);
            System.out.println("Valor Total a Pagar: R$ " + valorTotal);

            // Solicitar método de pagamento
            System.out.print("Método de Pagamento (Dinheiro, Cartão, PIX, etc.): ");
            String metodoPagamentoStr = scanner.nextLine();

            // Validar e converter o método de pagamento
            MetodoPagamento metodoPagamento;
            try {
                metodoPagamento = MetodoPagamento.valueOf(metodoPagamentoStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Método de pagamento inválido. Use Dinheiro, Cartão, PIX, etc.");
                return;
            }

            // Registrar o pagamento
            Pagamento pagamento = new Pagamento(locacao.getId(), valorTotal, dataDevolucaoReal, metodoPagamento);
            pagamentoController.adicionarPagamento(pagamento);

            // Atualizar status do veículo para disponível
            veiculo.setDisponivel(true);
            veiculoController.atualizarVeiculo(veiculo);

            // Atualizar status da locação
            locacao.setPagamento(pagamento);
            locacao.atualizarStatus();
            locacaoController.atualizarLocacao(locacao);

            System.out.println("Devolução registrada com sucesso!");
        } catch (VeiculoNaoEncontradoException | LocacaoNaoEncontradaException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace(); // Adicione esta linha para depuração
        }
    }
}