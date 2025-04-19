package view;

import controller.VeiculoController;
import model.Veiculo;
import model.exceptions.JsonCarregamentoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListagemView extends JFrame {

    private VeiculoController veiculoController;
    private JTextArea areaTexto;
    private JButton btnDisponiveis, btnLocados, btnVoltar;
    private MenuPrincipalView menuAnterior;

    public ListagemView(MenuPrincipalView menuAnterior) throws JsonCarregamentoException {
        this.menuAnterior = menuAnterior;
        this.veiculoController = new VeiculoController();

        configurarJanela();
        inicializarComponentes();
        adicionarEventos();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("LISTAGEM DE VEÍCULOS");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnDisponiveis = new JButton("Veículos Disponíveis");
        btnLocados = new JButton("Veículos Locados");
        btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnDisponiveis);
        painelBotoes.add(btnLocados);
        painelBotoes.add(btnVoltar);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaTexto);

        getContentPane().add(painelBotoes, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void adicionarEventos() {
        btnDisponiveis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarVeiculos(true);
            }
        });

        btnLocados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarVeiculos(false);
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

    private void listarVeiculos(boolean disponivel) {
        areaTexto.setText(""); // Limpa a área de texto
        List<Veiculo> veiculos = veiculoController.listarVeiculos();

        String titulo = disponivel ? "--- VEÍCULOS DISPONÍVEIS ---\n\n" : "--- VEÍCULOS LOCADOS ---\n\n";
        areaTexto.append(titulo);

        for (Veiculo v : veiculos) {
            if (v.isDisponivel() == disponivel) {
                areaTexto.append(v.toString() + "\n");
            }
            areaTexto.append("\n=========================\n");
        }
    }
}