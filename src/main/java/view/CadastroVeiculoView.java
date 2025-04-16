package view;

import controller.*;
import model.*;
import model.exceptions.JsonCarregamentoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroVeiculoView extends JFrame {
    private MenuPrincipalView menuAnterior;
    private VeiculoController controller;
    private JTextField txtPlaca;
    private JTextField txtModelo;
    private JTextField txtAno;
    private JComboBox<String> boxTipo;
    private JButton btnCadastrar;
    private JButton btnVoltar;

    public CadastroVeiculoView(MenuPrincipalView menuAnterior) throws JsonCarregamentoException {
        this.menuAnterior = menuAnterior;
        try {
            this.controller = new VeiculoController();  // Tratar a exceção aqui
        } catch (JsonCarregamentoException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage());
            return;  // Impede a criação da tela caso haja erro
        }
        setTitle("CADASTRO DE VEÍCULOS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        inicializarComponentes();
        setVisible(true);
    }

    public void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(10, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblPlaca = new JLabel("Placa");
        JLabel lblModelo = new JLabel("Modelo");
        JLabel lblAno = new JLabel("Ano");
        JLabel lblTipo = new JLabel("Tipo");
        String[] tipos = {"Carro", "Moto", "Caminhão"};
        txtPlaca = new JTextField();
        txtModelo = new JTextField();
        txtAno = new JTextField();
        boxTipo = new JComboBox<>(tipos);

        btnCadastrar = new JButton("CADASTRAR");
        btnVoltar = new JButton("VOLTAR");

        painel.add(lblPlaca);
        painel.add(txtPlaca);
        painel.add(lblModelo);
        painel.add(txtModelo);
        painel.add(lblAno);
        painel.add(txtAno);
        painel.add(lblTipo);
        painel.add(boxTipo);
        painel.add(btnCadastrar);
        painel.add(btnVoltar);

        add(painel);
        adicionarEventos();
    }

    public void adicionarEventos() {
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText();
                String modelo = txtModelo.getText();
                int ano = -1;
                try {
                    ano = Integer.parseInt(txtAno.getText());
                } catch (NumberFormatException x) {
                    JOptionPane.showMessageDialog(null, "Digite um ano válido.");
                }
                String tipo = (String) boxTipo.getSelectedItem();

                if (placa.isEmpty() || modelo.isEmpty() || ano == -1 || tipo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                }

                try { //Bloco para criar o veículo de acordo com seu tipo utilizando switch
                    Veiculo veiculo = null;
                    switch (tipo) {
                        case "Carro":
                            String tipoCarro = "Carro";
                            veiculo = new Carro(placa, modelo, ano, tipoCarro);
                            break;
                        case "Moto":
                            String tipoMoto = "Moto";
                            veiculo = new Moto(placa, modelo, ano, tipoMoto);
                            break;
                        case "Caminhão":
                            //    String tipoCaminhao = "Caminhão";
                            veiculo = new Caminhao(placa, modelo, ano, "Caminhão");
                            break;
                        default:
                            System.out.println("Tipo de veículo inválido.");
                            return;
                    }
                    JOptionPane.showMessageDialog(null, "Veiculo cadastrado com sucesso!");
                    controller.adicionarVeiculo(veiculo);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar veículo: " + ex.getMessage());

                }
            }
        });
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuAnterior.setVisible(true);
                dispose();
            }
        });
    }
}