package br.com.biblioteca.util;

import java.util.HashMap;
import java.util.Map;

public class GeradorDeId {
    private static final Map<String, Integer> contadores = new HashMap<>();

    public static int gerarId(String tipoEntidade) {
        int id = contadores.getOrDefault(tipoEntidade, 1);
        contadores.put(tipoEntidade, id + 1);
        return id;
    }
}
