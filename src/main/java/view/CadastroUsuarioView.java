package view;

import controller.*;
import model.exceptions.JsonCarregamentoException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroUsuarioView extends JFrame {
    private MenuPrincipalView menuAnterior;
    private UsuarioController controller;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JTextField txtTipo;
    private JButton btnCriar;
    private JButton btnVoltar;

    public CadastroUsuarioView(MenuPrincipalView menuAnterior) {
        this.menuAnterior = menuAnterior;
        try {
            this.controller = new UsuarioController();
        } catch (SQLException e) {
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
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUsuario = new JLabel("Usuário:");
        JLabel lblSenha = new JLabel("Senha:");
        JLabel lblTipo = new JLabel("Tipo:");

        txtUsuario = new JTextField();
        txtSenha = new JPasswordField();
        txtTipo = new JTextField();
        btnCriar = new JButton("CRIAR");
        btnVoltar = new JButton("VOLTAR");

        painel.add(lblUsuario);
        painel.add(txtUsuario);
        painel.add(lblSenha);
        painel.add(txtSenha);
        painel.add(lblTipo);
        painel.add(txtTipo);
        painel.add(new JLabel());
        painel.add(btnCriar);
        painel.add(btnVoltar);

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
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + ex.getMessage());
                }
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuAnterior.setVisible(true);
                dispose();
            }
        });
    }
}
