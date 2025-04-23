package view;

import controller.PagamentoController;
import model.exceptions.JsonCarregamentoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RelatorioView extends JFrame {

    private MenuPrincipalView menuAnterior;
    private JLabel lblFaturamento;
    private JButton btnGerarPDF, btnVoltar;

    public RelatorioView(MenuPrincipalView menuAnterior) {
        this.menuAnterior = menuAnterior;
        setTitle("Relatório de Pagamentos");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(3, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Exibe o faturamento
        PagamentoController controller = null;
        try {
            controller = new PagamentoController();
        } catch (JsonCarregamentoException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados: " + e.getMessage());
            return;
        }
        double faturamento = controller.faturamentoMensal();
        lblFaturamento = new JLabel("Faturamento do mês atual: R$ " + String.format("%.2f", faturamento));
        lblFaturamento.setHorizontalAlignment(SwingConstants.CENTER);

        btnVoltar = new JButton("VOLTAR");
        btnGerarPDF = new JButton("Gerar PDF do Relatório");


        painel.add(lblFaturamento);
        painel.add(btnGerarPDF);
        painel.add(btnVoltar);

        add(painel);
        adicionarEventos();

    }


    private void adicionarEventos(){
        btnGerarPDF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gerarRelatorioPDF();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuAnterior.setVisible(true);
                dispose();
            }
        });
    }


    private void gerarRelatorioPDF() {

        // Abrir o JFileChooser para o usuário escolher onde salvar o arquivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha o local para salvar o relatório");

        // Permite escolher o arquivo (não apenas o diretório)
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Configura o nome padrão do arquivo
        fileChooser.setSelectedFile(new File("relatorio_pagamento_mensal.pdf"));

        // Exibe o dialogo de salvar
        int resultado = fileChooser.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            // O usuário escolheu o caminho para salvar o arquivo
            File arquivoSelecionado = fileChooser.getSelectedFile();

            try {
                // Gera o relatório PDF no caminho escolhido
                RelatorioPagamentoPDF pdf = new RelatorioPagamentoPDF();
                pdf.gerarRelatorioMensal(arquivoSelecionado.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Relatório PDF gerado com sucesso em: " + arquivoSelecionado.getAbsolutePath());
            } catch (JsonCarregamentoException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar os dados: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Operação de salvamento cancelada.");
        }
    }
}
