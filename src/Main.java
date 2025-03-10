package main;

import view.TelaPrincipal;
import model.exceptions.JsonCarregamentoException;

public class Main {
    public static void main(String[] args) {
        try {
            // Inicializa a Tela Principal
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.exibirTela(); // Exibe o menu principal
        } catch (JsonCarregamentoException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace(); // Exibe o rastreamento da exceção para depuração
        }
    }
}