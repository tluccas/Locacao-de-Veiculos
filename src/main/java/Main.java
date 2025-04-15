import view.LoginView;
import model.exceptions.JsonCarregamentoException;

public class Main {
    public static void main(String[] args) {
        try {
            // Inicializa a Tela Principal
            new LoginView();
             // Exibe o menu principal
        } catch (JsonCarregamentoException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace(); // Exibe o rastreamento da exceção para depuração
        }
    }
}