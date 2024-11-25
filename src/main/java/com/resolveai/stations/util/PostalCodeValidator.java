package com.resolveai.stations.util;

import java.net.HttpURLConnection;
import java.util.Scanner;
import java.net.URL;

public class PostalCodeValidator {

    public static boolean isCepValid(String cep) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(url.openStream());
                String inline = "";
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                // Verifica se a resposta da API indica erro de CEP inválido
                return !inline.contains("\"erro\": true");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();  // Desconecta a conexão
            }
        }
        return false;
    }

}
