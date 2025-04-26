package view;

import com.toedter.calendar.JDateChooser;
import controller.*;
import model.*;
import model.exceptions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PagamentoView extends JFrame {

    private MenuPrincipalView menuAnterior;
    private LocacaoController locacaoController;
    private PagamentoController pagamentoController;
    private VeiculoController veiculoController;
    private JDateChooser devolucaoRealDateChooser;
    private JTextField txtPlaca;
    private JComboBox<String> boxMetodoPagamento;
    private JButton btnVoltar;
    private JButton btnConfirmar;
    private JLabel lblValorTotal;

    public PagamentoView(MenuPrincipalView menuAnterior) throws JsonCarregamentoException {
        this.menuAnterior = menuAnterior;
        try {
            this.locacaoController = new LocacaoController();
            this.pagamentoController = new PagamentoController();
            this.veiculoController = new VeiculoController();

        } catch (JsonCarregamentoException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage());
            return;  // Impede a criação da tela caso haja erro
        }

        setTitle("REGISTRO DE PAGAMENTOS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        inicializarComponentes();
        setVisible(true);
    }

    public void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(7, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblPlaca = new JLabel("Placa do veículo: ");
        JLabel lblDevolucao = new JLabel("Data de Devolução: ");
        JLabel lblMetodoPagamento = new JLabel("Método de Pagamento: ");
        String[] metodo = {"DINHEIRO", "CARTÃO DE CRÉDITO", "CARTÃO DE DÉBITO", "PIX"};
        JLabel lblValorTitulo = new JLabel("Valor Total:");
        lblValorTotal = new JLabel("R$ 0,00");

        txtPlaca = new JTextField();
        boxMetodoPagamento = new JComboBox<>(metodo);
        btnVoltar = new JButton("VOLTAR");
        btnConfirmar = new JButton("CONFIRMAR PAGAMENTO");


        devolucaoRealDateChooser = new JDateChooser();
        devolucaoRealDateChooser.setDateFormatString("yyyy-MM-dd");
        //Mostrar o valor do pagamento em tempo real
        devolucaoRealDateChooser.getDateEditor().addPropertyChangeListener(e -> {
            if ("date".equals(e.getPropertyName())) {
                atualizarValorTotal();
            }
        });

        painel.add(lblPlaca);
        painel.add(txtPlaca);
        painel.add(lblDevolucao);
        painel.add(devolucaoRealDateChooser);
        painel.add(lblMetodoPagamento);
        painel.add(boxMetodoPagamento);
        painel.add(lblValorTitulo);
        painel.add(lblValorTotal);
        painel.add(btnConfirmar);
        painel.add(btnVoltar);

        add(painel);
        adicionarEventos();
    }

    public void adicionarEventos() {
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText();
                MetodoPagamento metodoPagamento = MetodoPagamento.valueOf((String) boxMetodoPagamento.getSelectedItem());

                if (placa.isEmpty() || devolucaoRealDateChooser.getDate() == null || metodoPagamento == null) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                }

                try {
                    if (veiculoController.buscarPorPlaca(placa) == null) {
                        JOptionPane.showMessageDialog(null, "Veículo de placa" + placa + " não encontrado!");
                    } else if (veiculoController.buscarPorPlaca(placa).isDisponivel()) {
                        JOptionPane.showMessageDialog(null, "Veículo de placa" + placa + " não possui locação ativa!");
                    }
                } catch (VeiculoNaoEncontradoException ex) {
                    throw new RuntimeException(ex);
                }

                //Executar o processo de pagamento
                try {

                    processarPagamento(placa, metodoPagamento);

                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
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

    public void processarPagamento(String placa, MetodoPagamento metodoPagamento) throws JsonCarregamentoException {

        //Buscar a locação por placa
        Locacao locacao = locacaoController.buscarLocacaoPorVeiculo(placa);

        //Converte para local date e calcula o valor
        LocalDate dataDevolucao = devolucaoRealDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        double valorTotal = locacao.calcularValorTotal(dataDevolucao);

        try {
            Pagamento pagamento = new Pagamento(locacao.getId(), valorTotal, dataDevolucao, metodoPagamento);
            pagamentoController.adicionarPagamento(pagamento);

            // Atualizar status do veículo para disponível
            Veiculo veiculo = veiculoController.buscarPorPlaca(placa);
            veiculo.setDisponivel(true);
            veiculoController.atualizarVeiculo(veiculo);

            // Atualizar status da locação
            locacao.setPagamento(pagamento);
            locacao.atualizarStatus();
            locacaoController.atualizarLocacao(locacao);
            JOptionPane.showMessageDialog(null, "Pagamento registrado com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar Pagamento: " + ex.getMessage());
        }
    }

    //metodo para mostrar em tempo real o valor do pagamento
    private void atualizarValorTotal() {
        try {
            // Getar os valores
            String placa = txtPlaca.getText().trim();
            Date dataSelecionada = devolucaoRealDateChooser.getDate();

            // Verificar campos preenchidos
            if (placa.isEmpty() || dataSelecionada == null) {
                lblValorTotal.setText("Valor: R$ 0,00");
                return;
            }

            // Converter para LocalDate
            LocalDate dataDevolucao = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Buscar locação e calcular valor
            Locacao locacao = locacaoController.buscarLocacaoPorVeiculo(placa);
            if (locacao == null) {
                lblValorTotal.setText("Locação não encontrada");
                return;
            }

            double valor = locacao.calcularValorTotal(dataDevolucao);
            lblValorTotal.setText(String.format("Valor: R$ %.2f", valor));

        } catch (Exception e) {
            lblValorTotal.setText("Erro no cálculo");
            System.err.println("Erro ao atualizar valor: " + e.getMessage()); //mostra o erro no terminal (testando)
        }
    }

}