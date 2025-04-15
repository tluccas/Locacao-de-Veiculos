package view;

import controller.ClienteController;
import controller.UsuarioController;
import model.exceptions.JsonCarregamentoException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Scanner;

public class CadastroClienteView extends JFrame {
    private MenuPrincipalView menuAnterior;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JButton btnVoltar;
    private JButton btnCadastrar;
    private ClienteController controller;

    public CadastroClienteView(MenuPrincipalView menuAnterior) throws JsonCarregamentoException {
        this.menuAnterior = menuAnterior;
        try {
            this.controller = new ClienteController();  // Tratar a exceção aqui
        } catch (JsonCarregamentoException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage());
            return;  // Impede a criação da tela caso haja erro
        }

        setTitle("CADASTRO DE CLIENTES");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        inicializarComponentes();
        setVisible(true);
    }

    public void inicializarComponentes(){
        JPanel painel = new JPanel(new GridLayout(8,2,10,10));
        painel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel lblNome = new  JLabel("Nome: ");
        JLabel lblCpf = new JLabel("CPF: ");
        JLabel lblEmail = new JLabel("E-Mail: ");

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtEmail = new JTextField();
        btnVoltar = new JButton("VOLTAR");
        btnCadastrar = new JButton("CADASTRAR");

        painel.add(lblNome);
        painel.add(txtNome);
        painel.add(lblCpf);
        painel.add(txtCpf);
        painel.add(lblEmail);
        painel.add(txtEmail);
        painel.add(btnCadastrar);
        painel.add(btnVoltar);

        add(painel);
        adicionarEventos();
    }

    public void adicionarEventos(){
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                String email = txtCpf.getText();

                if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                }

                try {
                    controller.adicionarCliente(nome, cpf, email);
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                } catch (JsonCarregamentoException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + ex.getMessage());
                }

            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuAnterior.setVisible(true);
                dispose(); // fecha a janela atual

            }
        });

    }

}