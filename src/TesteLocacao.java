import model.*;

import java.time.LocalDate;

public class TesteLocacao {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("João Silva", "123.456.789-00", "joao@email.com");
        Veiculo veiculo = new Carro("ABC-1234", "Fusca", 1980);
        LocalDate dataRetirada = LocalDate.now();
        LocalDate dataDevolucao = dataRetirada.plusDays(5);

        // Cria uma locação
        Locacao locacao = new Locacao(cliente, veiculo, dataRetirada, dataDevolucao);

        // Verifica se o veículo foi marcado como indisponível
        System.out.println("Veículo disponível? " + veiculo.isDisponivel()); // Deve imprimir "false"

        // Registra um pagamento
        locacao.registrarPagamento(500.0, MetodoPagamento.CARTAO_CREDITO);

        // Exibe os detalhes da locação
        System.out.println(locacao);
        System.out.println("Pagamento: " + locacao.getPagamento());
    }
}