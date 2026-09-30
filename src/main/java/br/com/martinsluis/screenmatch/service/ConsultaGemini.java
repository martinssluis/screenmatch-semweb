package br.com.martinsluis.screenmatch.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConsultaGemini {
    @Value("${AI_API_KEY}")
    private String token;

    public String obterTraducao(String texto) {
        Client client = Client.builder().apiKey(token).build();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3-flash-preview",
                        "Traduza para língua portuguesa o seguinte trecho da forma mais direta, não pergunte mais nada, apenas faça: " + texto,
                        null);

        return response.text();
    }
}
