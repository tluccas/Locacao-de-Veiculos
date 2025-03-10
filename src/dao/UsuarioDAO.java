package dao;

import com.google.gson.reflect.TypeToken;
import model.Administrador;
import model.Atendente;
import model.Gerente;
import model.Usuario;
import model.exceptions.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String USUARIOS_FILE = "usuarios.json";
    private List<Usuario> usuarios;

    public UsuarioDAO() throws JsonCarregamentoException {
   
        Type tipoUsuario = new TypeToken<ArrayList<Usuario>>() {
        }.getType();
        usuarios = Persistencia.carregarDados(USUARIOS_FILE, tipoUsuario);

        if (usuarios == null) {
            usuarios = new ArrayList<>();
            Administrador admin1 = new Administrador("admin", "admin", "Administrador");
            usuarios.add(admin1);
        }
        //Cria um administrador padrao
    

    }


    //Método p carregar novamente usuarios
    public static List<Usuario> carregarUsuarios() throws JsonCarregamentoException {
        Type tipoUsuario = new TypeToken<ArrayList<Usuario>>() {}.getType();
        return Persistencia.carregarDados(USUARIOS_FILE, tipoUsuario);
    }
    // Método p salvar usuarios
    public void salvarUsuarios() {
        Persistencia.salvarDados(USUARIOS_FILE, usuarios);
    }

    public void adicionarUsuario(String usuario, String senha, String tipo) throws JsonCarregamentoException {
        Usuario user;

        switch (tipo) {
            case "Administrador":
                user = new Administrador(usuario, senha, tipo);
                break;
            case "Gerente":
                user = new Gerente(usuario, senha, tipo);
                break;
            case "Atendente":
                user = new Atendente(usuario, senha, tipo);
                break;
            default:
                throw new IllegalStateException("Erro tipo inexistente: " + tipo);
        }

        usuarios.add(user);
        salvarUsuarios();

    }

    public Usuario buscarUsuario(String usuario, String senha) throws UsuarioNaoEncontradoException, SenhaOuUserIncorretoException {
        boolean encontrado = false;

        for (Usuario user : usuarios) {
            if (user.getNome().equals(usuario)) {
                encontrado = true;
                if (user.getSenha().equals(senha)) {
                    return user;
                }
            }
        }
        if (!encontrado) {
            throw new UsuarioNaoEncontradoException("Usuário inexistente!");

        }else{
            throw new SenhaOuUserIncorretoException("Usuario ou senha incorretos!");
        }
    }

}
