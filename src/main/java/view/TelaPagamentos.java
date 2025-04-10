package view;

import controller.PagamentoController;
import model.Pagamento;
import model.exceptions.JsonCarregamentoException;

import java.util.List;
import java.util.Scanner;

public class TelaPagamentos {
    private Scanner scanner;
    private PagamentoController pagamentoController;

    public TelaPagamentos() throws JsonCarregamentoException {
        this.scanner = new Scanner(System.in);
        this.pagamentoController = new PagamentoController();
    }

    public void exibirTela() {
        System.out.println("\n--- LISTAGEM DE RELATÓRIOS ---");
        System.out.println("1. Listagem pagamentos gerais.");
        System.out.println("2. Listagem faturamento mensal.");
        System.out.println("3. Relatório em pdf.");
        int opcao = scanner.nextInt();
        switch (opcao) {
            case 1:
            // Listar todos os pagamentos
            List<Pagamento> pagamentos = pagamentoController.listarPagamentos();
            if (pagamentos.isEmpty()) {
                System.out.println("Nenhum pagamento registrado.");
            } else {
                for (Pagamento pagamento : pagamentos) {
                    System.out.println(pagamento);
                }
            }
            break;
            case 2:
                System.out.println("--- FATURAMENTO MENSAL ---");
                double faturamento = pagamentoController.faturamentoMensal(); // Captura o faturamento
                if (faturamento == 0.0) {
                    System.out.println("Nenhum pagamento registrado neste mês.");
                } else {
                    System.out.printf("Faturamento do mês: R$ %.2f%n", faturamento);
                }
                break;
            case 3:
                System.out.println("Em desenvolvimento...");
                break;
        }
    }
}