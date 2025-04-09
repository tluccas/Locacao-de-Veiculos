package view;

import controller.UsuarioController;
import model.Usuario;
import model.exceptions.JsonCarregamentoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public TelaPrincipal() throws JsonCarregamentoException {
        setTitle("Sistema de Locação de Veículos");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUsuario = new JLabel("Usuário:");
        JLabel lblSenha = new JLabel("Senha:");

        txtUsuario = new JTextField();
        txtSenha = new JPasswordField();
        btnEntrar = new JButton("Entrar");

        painel.add(lblUsuario);
        painel.add(txtUsuario);
        painel.add(lblSenha);
        painel.add(txtSenha);
        painel.add(new JLabel());
        painel.add(btnEntrar);

        add(painel);
        adicionarEventos();
    }

    private void adicionarEventos() {
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String senha = new String(txtSenha.getPassword());

                if (usuario.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                    return;
                }

                UsuarioController controller = null;
                try {
                    controller = new UsuarioController();
                } catch (JsonCarregamentoException ex) {
                    throw new RuntimeException(ex);
                }
                Usuario usuarioLogado = controller.fazerLogin(usuario, senha);

                if (usuarioLogado != null) {
                    JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
                    dispose();
                    new MenuPrincipalView(usuarioLogado); // Certifique-se que esta classe exista
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
                }
            }
        });
    }
}
