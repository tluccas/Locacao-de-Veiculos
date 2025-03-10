package controller;

import dao.UsuarioDAO;
import model.Usuario;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.SenhaOuUserIncorretoException;
import model.exceptions.UsuarioNaoEncontradoException;

import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() throws JsonCarregamentoException {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Método para adicionar um novo usuário
    public void adicionarUsuario(String usuario, String senha, String tipo) throws JsonCarregamentoException {
        usuarioDAO.adicionarUsuario(usuario, senha, tipo);
    }

    // Método para fazer login
    public Usuario fazerLogin(String usuario, String senha) throws UsuarioNaoEncontradoException, SenhaOuUserIncorretoException {
        return usuarioDAO.buscarUsuario(usuario, senha);
    }

    // Método p/ salvar no json
    public void salvarUsuario(){
        usuarioDAO.salvarUsuarios();
    }

    //Método p atualizar lista de usuarios
    public List<Usuario> atualizarListaUsuarios() throws JsonCarregamentoException {
        return UsuarioDAO.carregarUsuarios();
    }
}