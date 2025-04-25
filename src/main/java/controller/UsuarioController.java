package controller;

import dao.UsuarioDAO;
import model.Usuario;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.SenhaOuUserIncorretoException;
import model.exceptions.UsuarioNaoEncontradoException;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() throws SQLException {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Método para adicionar um novo usuário
    public void adicionarUsuario(String usuario, String senha, String tipo) throws SQLException {
        Usuario u = usuarioDAO.criarUsuarioPorTipo(usuario, senha, tipo);
        usuarioDAO.adicionarUsuario(u);
    }

    // Método para fazer login
    public Usuario fazerLogin(String usuario, String senha) throws UsuarioNaoEncontradoException, SenhaOuUserIncorretoException {
        return usuarioDAO.buscarUsuario(usuario, senha);
    }


}