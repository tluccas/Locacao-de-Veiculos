package view;

import controller.*;
import model.exceptions.JsonCarregamentoException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class TelaCadastroUsuario extends JFrame {
    private UsuarioController controller;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JTextField txtTipo;
    private JButton btnCriar;

    public TelaCadastroUsuario() {
        try {
            this.controller = new UsuarioController();  // Tratar a exceção aqui
        } catch (JsonCarregamentoException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage());
            return;  // Impede a criação da tela caso haja erro
        }

        setTitle("CADASTRO DE USUÁRIO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        inicializarComponentes();
        setVisible(true);
    }

    public void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(4, 4, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUsuario = new JLabel("Usuário:");
        JLabel lblSenha = new JLabel("Senha:");
        JLabel lblTipo = new JLabel("Tipo:");

        txtUsuario = new JTextField();
        txtSenha = new JPasswordField();
        txtTipo = new JTextField();
        btnCriar = new JButton("CRIAR");

        painel.add(lblUsuario);
        painel.add(txtUsuario);
        painel.add(lblSenha);
        painel.add(txtSenha);
        painel.add(lblTipo);
        painel.add(txtTipo);
        painel.add(new JLabel());
        painel.add(btnCriar);

        add(painel);
        adicionarEventos();
    }

    public void adicionarEventos() {
        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String senha = new String(txtSenha.getPassword());
                String tipo = txtTipo.getText();

                if (usuario.isEmpty() || senha.isEmpty() || tipo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                    return;
                }

                try {
                    controller.adicionarUsuario(usuario, senha, tipo);
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                } catch (JsonCarregamentoException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + ex.getMessage());
                }
            }
        });
    }
}















/*public class TelaCadastroUsuario {
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
}*/
