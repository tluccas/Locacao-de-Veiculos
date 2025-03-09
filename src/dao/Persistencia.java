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
        File arquivo = new File(caminhoArquivo);

        // Se o arquivo não existir, cria um arquivo JSON vazio
        if (!arquivo.exists()) {
            try (Writer writer = new FileWriter(arquivo)) {
                writer.write("[]"); // Cria uma lista vazia como conteúdo inicial
                System.out.println("Arquivo criado: " + caminhoArquivo);
            } catch (IOException e) {
                throw new JsonCarregamentoException("Erro ao criar o arquivo JSON: " + caminhoArquivo, e);
            }
        }

        // Carrega os dados do arquivo JSON
        try (Reader reader = new FileReader(arquivo)) {
            T dados = gson.fromJson(reader, tipo); // Converte o JSON em um objeto do tipo especificado

            // Verifica se os dados foram carregados corretamente
            if (dados == null) {
                throw new JsonCarregamentoException("Arquivo JSON vazio ou inválido: " + caminhoArquivo, null);
            }

            return dados;
        } catch (FileNotFoundException e) {
            throw new JsonCarregamentoException("Arquivo não encontrado: " + caminhoArquivo, e);
        } catch (IOException e) {
            throw new JsonCarregamentoException("Erro de leitura no arquivo: " + caminhoArquivo, e);
        } catch (JsonSyntaxException e) {
            throw new JsonCarregamentoException("Erro de sintaxe no arquivo JSON: " + caminhoArquivo, e);
        }
    }
}

