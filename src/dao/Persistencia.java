package dao;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.exceptions.JsonCarregamentoException;

import java.io.*;
import java.lang.reflect.Type;

public class Persistencia {

    private static final Gson gson = new Gson(); // Objeto Gson para serialização/desserialização

    // Método para salvar dados em um arquivo JSON
    public static void salvarDados(String caminhoArquivo, Object dados) {
        try (Writer writer = new FileWriter(caminhoArquivo)) {
            gson.toJson(dados, writer); // Converte o objeto para JSON e salva no arquivo
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo: " + e.getMessage());
        }
    }

    // Método para carregar dados de um arquivo JSON
    public static <T> T carregarDados(String caminhoArquivo, Type tipo) throws JsonCarregamentoException {
        System.out.println("Carregando arquivo: " + caminhoArquivo);
        try (Reader reader = new FileReader(caminhoArquivo)) {
            return gson.fromJson(reader, tipo); // Converte o JSON em um objeto do tipo especificado
        } catch (FileNotFoundException e) {
            throw new JsonCarregamentoException("Arquivo não encontrado: " + caminhoArquivo, e);
        } catch (IOException e) {
            return null;
        }
    }
}

