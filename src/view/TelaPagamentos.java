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
        System.out.println("\n--- LISTAGEM DE PAGAMENTOS ---");

        // Listar todos os pagamentos
        List<Pagamento> pagamentos = pagamentoController.listarPagamentos();
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
        } else {
            for (Pagamento pagamento : pagamentos) {
                System.out.println(pagamento);
            }
        }
    }
}