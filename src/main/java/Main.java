import view.LoginView;
import model.exceptions.JsonCarregamentoException;
import dao.*;
public class Main {
    public static void main(String[] args) throws JsonCarregamentoException {
        PagamentoDAO dao = new PagamentoDAO();

        dao.listarPagamentos();
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