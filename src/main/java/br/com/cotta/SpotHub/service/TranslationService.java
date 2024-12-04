package br.com.cotta.SpotHub.service;

import br.com.cotta.SpotHub.client.GoogleTranslateClient;
import br.com.cotta.SpotHub.dto.in.TranslationResponse;
import br.com.cotta.SpotHub.dto.out.TranslationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
    @Autowired
    private GoogleTranslateClient googleTranslateClient;
    @Value("${google-api-key}")
    private String apiKey;

    public String translateText(String text, String targetLanguage) {
        TranslationRequest request = new TranslationRequest(text, targetLanguage);
        TranslationResponse response = googleTranslateClient.translate(apiKey, request);
        return response.getData().getTranslations().get(0).getTranslatedText();
    }
}
