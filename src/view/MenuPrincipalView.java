package view;

import model.Usuario;
import model.exceptions.JsonCarregamentoException;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {

    private Usuario usuario;

    public MenuPrincipalView(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Menu Principal - " + usuario.getTipo());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (usuario.getTipo().equalsIgnoreCase("Administrador")) {
            JButton btnUsuarios = new JButton("Usuários");
            painel.add(btnUsuarios);
            btnUsuarios.addActionListener(e -> {
                try {
                    new TelaCadastroUsuario();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        if (usuario.getTipo().equalsIgnoreCase("Administrador") || usuario.getTipo().equalsIgnoreCase("Gerente")) {
            JButton btnClientes = new JButton("Clientes");
            JButton btnVeiculos = new JButton("Veículos");
            JButton btnRelatorios = new JButton("Relatórios");

            painel.add(btnClientes);
            painel.add(btnVeiculos);
            painel.add(btnRelatorios);

            btnClientes.addActionListener(e -> {
                try {
                    new TelaCadastroCliente();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnVeiculos.addActionListener(e -> {
                try {
                    new TelaCadastroVeiculo();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnRelatorios.addActionListener(e -> {
                try {
                    new TelaListagem();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        if (usuario.getTipo().equalsIgnoreCase("Atendente")) {
            JButton btnClientes = new JButton("Clientes");
            JButton btnVeiculos = new JButton("Veículos");
            JButton btnLocacoes = new JButton("Locações");
            JButton btnPagamentos = new JButton("Pagamentos");
            JButton btnDevolucao = new JButton("Devoluções");

            painel.add(btnClientes);
            painel.add(btnVeiculos);
            painel.add(btnLocacoes);
            painel.add(btnPagamentos);
            painel.add(btnDevolucao);

            btnClientes.addActionListener(e -> {
                try {
                    new TelaCadastroCliente();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnVeiculos.addActionListener(e -> {
                try {
                    new TelaCadastroVeiculo();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnLocacoes.addActionListener(e -> {
                try {
                    new TelaLocacao();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnPagamentos.addActionListener(e -> {
                try {
                    new TelaPagamentos();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnDevolucao.addActionListener(e -> {
                try {
                    new TelaDevolucao();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        add(painel);
    }
}
