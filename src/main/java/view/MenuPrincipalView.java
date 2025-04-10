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
                    new view.TelaCadastroUsuario();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        if (usuario.getTipo().equalsIgnoreCase("Administrador") || usuario.getTipo().equalsIgnoreCase("Gerente")) {
            JButton btnClientes = new JButton("Cadastro De Clientes");
            JButton btnVeiculos = new JButton("Cadastro De Veículos");
            JButton btnRelatorios = new JButton("Visualizar Relatórios");

            painel.add(btnClientes);
            painel.add(btnVeiculos);
            painel.add(btnRelatorios);

            btnClientes.addActionListener(e -> {
                try {
                    new view.TelaCadastroCliente();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnVeiculos.addActionListener(e -> {
                try {
                    new view.TelaCadastroVeiculo();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnRelatorios.addActionListener(e -> {
                try {
                    new view.TelaListagem();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        if (usuario.getTipo().equalsIgnoreCase("Atendente")) {
            JButton btnClientes = new JButton("Cadastro De Clientes");
            JButton btnVeiculos = new JButton("Listar Veículos");
            JButton btnLocacoes = new JButton("Registrar Locação");
            JButton btnPagamentos = new JButton("Pagamentos Gerais");
            JButton btnDevolucao = new JButton("Registrar Devolução e Pagamento");

            painel.add(btnClientes);
            painel.add(btnVeiculos);
            painel.add(btnLocacoes);
            painel.add(btnPagamentos);
            painel.add(btnDevolucao);

            btnClientes.addActionListener(e -> {
                try {
                    new view.TelaCadastroCliente();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnVeiculos.addActionListener(e -> {
                try {
                    new view.TelaCadastroVeiculo();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnLocacoes.addActionListener(e -> {
                try {
                    new view.TelaLocacao();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnPagamentos.addActionListener(e -> {
                try {
                    new view.TelaPagamentos();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnDevolucao.addActionListener(e -> {
                try {
                    new view.TelaDevolucao();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        add(painel);
    }
}
