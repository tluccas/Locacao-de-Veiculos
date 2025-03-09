
package controller;

import dao.UsuarioDAO;
import model.Usuario;
import model.exceptions.JsonCarregamentoException;
import model.exceptions.SenhaOuUserIncorretoException;
import model.exceptions.UsuarioNaoEncontradoException;

public class LoginController {
    private UsuarioDAO usuarioDAO;

    public LoginController() throws JsonCarregamentoException {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    
    public Usuario fazerLogin(String login, String senha) throws UsuarioNaoEncontradoException, SenhaOuUserIncorretoException {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("O login não pode ser vazio.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }

        // Busca o usuário no DAO
        Usuario usuario = usuarioDAO.buscarUsuario(login, senha);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado: " + login);
        }

        // Verifica se a senha está correta
        if (!usuario.getSenha().equals(senha)) {
            throw new SenhaOuUserIncorretoException("Senha incorreta para o usuário: " + login);
        }

        return usuario; // Retorna o usuário autenticado
    }
}