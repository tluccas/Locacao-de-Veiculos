package view;

import controller.*;
import model.exceptions.JsonCarregamentoException;

import java.util.Scanner;

public class TelaCadastroUsuario {
        private Scanner scanner;
        private UsuarioController usuarioController;

        public TelaCadastroUsuario() throws JsonCarregamentoException {
            this.scanner = new Scanner(System.in);
            this.usuarioController = new UsuarioController();
        }

        public void exibirTela() {
        System.out.println("\n--- CADASTRO DE USUÁRIO ---");
        System.out.print("Nome de usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Tipo (Administrador, Gerente, Atendente): ");
        String tipo = scanner.nextLine();

        try {
            usuarioController.adicionarUsuario(usuario, senha, tipo);
            System.out.println("Salvando...");
            usuarioController.salvarUsuario();
            usuarioController.atualizarListaUsuarios();
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (JsonCarregamentoException e) {
            throw new RuntimeException(e);
        }
        }
}
