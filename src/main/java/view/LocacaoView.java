package view;


import controller.*;
import com.toedter.calendar.JDateChooser;
import model.Cliente;
import model.Locacao;
import model.Veiculo;
import model.exceptions.JsonCarregamentoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

public class LocacaoView extends JFrame {
    private MenuPrincipalView menuAnterior;
    private LocacaoController locacaoController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;
    private JDateChooser retiradaDateChooser;
    private JDateChooser devolucaoDateChooser;
    private JTextField txtCpf;
    private JTextField txtPlaca;
    private JButton btnVoltar;
    private JButton btnRegistrar;

    public LocacaoView(MenuPrincipalView menuAnterior) throws JsonCarregamentoException {
        this.menuAnterior = menuAnterior;
        try {
            this.locacaoController = new LocacaoController();
            this.clienteController = new ClienteController();
            this.veiculoController = new VeiculoController();

        } catch (JsonCarregamentoException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage());
            return;  // Impede a criação da tela caso haja erro
        }

        setTitle("REGISTRO DE LOCAÇÕES");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        inicializarComponentes();
        setVisible(true);
    }

    public void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(8, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblCpf = new JLabel("CPF: ");
        JLabel lblPlaca = new JLabel("Placa do veículo: ");
        JLabel lblRetirada = new JLabel("Data de Retirada: ");
        JLabel lblDevolucao = new JLabel("Data de Devolução: ");

        txtCpf = new JTextField();
        txtPlaca = new JTextField();
        btnVoltar = new JButton("VOLTAR");
        btnRegistrar = new JButton("REGISTRAR");

        retiradaDateChooser = new JDateChooser();
        retiradaDateChooser.setDateFormatString("yyyy-MM-dd");
        devolucaoDateChooser = new JDateChooser();
        devolucaoDateChooser.setDateFormatString("yyyy-MM-dd");

        painel.add(lblCpf);
        painel.add(txtCpf);
        painel.add(lblPlaca);
        painel.add(txtPlaca);
        painel.add(retiradaDateChooser);
        painel.add(lblDevolucao);
        painel.add(devolucaoDateChooser);
        painel.add(btnRegistrar);
        painel.add(btnVoltar);

        add(painel);
        adicionarEventos();
    }

    public void adicionarEventos() {
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = txtCpf.getText();
                String placa = txtPlaca.getText();

                if (cpf.isEmpty() || placa.isEmpty() || retiradaDateChooser.getDate() == null || devolucaoDateChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                }

                if (veiculoController.buscarPorPlaca(placa) == null) {
                    JOptionPane.showMessageDialog(null, "Veículo de placa" + placa + " não cadastrado!");
                }else if(!veiculoController.buscarPorPlaca(placa).isDisponivel()){
                    JOptionPane.showMessageDialog(null, "Veículo de placa" + placa + " está locado!");
                }

                if (clienteController.buscarCliente(cpf) == null) {
                    JOptionPane.showMessageDialog(null, "Cliente não registrado!");
                }

                Cliente cliente = clienteController.buscarCliente(cpf);
                Veiculo veiculo = veiculoController.buscarPorPlaca(placa);
                LocalDate dataRetirada = retiradaDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dataDevolucao = devolucaoDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                try {
                    Locacao locacao = new Locacao(cliente, veiculo, dataRetirada, dataDevolucao);
                    locacaoController.adicionarLocacao(locacao);
                    veiculoController.atualizarDisponibilidade(veiculo, false);
                    JOptionPane.showMessageDialog(null, "Locação registrada com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao registrar Locacão: " + ex.getMessage());
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

/*public class LocacaoView {
    private Scanner scanner;
    private LocacaoController locacaoController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;

    public LocacaoView() throws JsonCarregamentoException {
        this.scanner = new Scanner(System.in);
        this.locacaoController = new LocacaoController();
        this.clienteController = new ClienteController();
        this.veiculoController = new VeiculoController();
    }

    public void exibirTela() throws VeiculoNaoEncontradoException {
        System.out.println("\n--- REGISTRO DE LOCAÇÃO ---");

        // Solicitar CPF do cliente
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();
        Cliente cliente = clienteController.buscarCliente(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        // Solicitar placa do veículo
        System.out.print("Placa do Veículo: ");
        String placa = scanner.nextLine();
        Veiculo veiculo = veiculoController.buscarPorPlaca(placa);
        if (veiculo == null || !veiculo.isDisponivel()) {
            System.out.println("Veículo não encontrado ou indisponível.");
            return;
        }

        // Solicitar datas de retirada e devolução
        System.out.print("Data de Retirada (AAAA-MM-DD): ");
        LocalDate dataRetirada = LocalDate.parse(scanner.nextLine());
        System.out.print("Data de Devolução (AAAA-MM-DD): ");
        LocalDate dataDevolucao = LocalDate.parse(scanner.nextLine());

        // Registrar a locação
        Locacao locacao = new Locacao(cliente, veiculo, dataRetirada, dataDevolucao);
        locacaoController.adicionarLocacao(locacao);

        // Marcar o veículo como indisponível
        veiculo.setDisponivel(false);
        veiculoController.atualizarVeiculo(veiculo);

        System.out.println("Locação registrada com sucesso!");
    }
}*/