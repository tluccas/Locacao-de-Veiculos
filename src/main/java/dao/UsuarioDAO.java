package dao;

import model.Administrador;
import model.Atendente;
import model.Gerente;
import model.Usuario;
import model.exceptions.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    Dotenv dotenv = Dotenv.load();

    String DB_URL = dotenv.get("DB_URL");
    String DB_USER = dotenv.get("DB_USER");
    String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    public UsuarioDAO() throws SQLException {
        if (listarUsuarios().isEmpty()) {
            Usuario u = new Administrador("admin", "admin", "Administrador");
            adicionarUsuario(u);
        }
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void adicionarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (usuario, senha, tipo) VALUES (?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getTipo());
            System.out.println("Executando SQL: " + sql); //Teste para o banco
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) { // Teste para o banco de dados
                System.out.println("Usuário inserido com sucesso.");
            } else {
                System.out.println("Nenhuma linha foi afetada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            e.printStackTrace();  // Imprimir o stack trace completo para ajudar no diagnóstico
        }
    }

    public Usuario buscarUsuario(String usuario, String senha) throws UsuarioNaoEncontradoException, SenhaOuUserIncorretoException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String senhaBanco = rs.getString("senha");
                String tipo = rs.getString("tipo");

                if (!senhaBanco.equals(senha)) {
                    throw new SenhaOuUserIncorretoException("Usuário ou senha incorretos!");
                }

                return criarUsuarioPorTipo(usuario, senha, tipo);
            } else {
                throw new UsuarioNaoEncontradoException("Usuário inexistente!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário");
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String usuario = rs.getString("usuario");
                String senha = rs.getString("senha");
                String tipo = rs.getString("tipo");

                usuarios.add(criarUsuarioPorTipo(usuario, senha, tipo));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    //Para instanciar usuario de acordo com seu nivel de acesso
    public Usuario criarUsuarioPorTipo(String usuario, String senha, String tipo) {
        switch (tipo) {
            case "Administrador":
                return new Administrador(usuario, senha, tipo);
            case "Gerente":
                return new Gerente(usuario, senha, tipo);
            case "Atendente":
                return new Atendente(usuario, senha, tipo);
            default:
                throw new IllegalStateException("Tipo de usuário inválido: " + tipo);
        }
    }
}
