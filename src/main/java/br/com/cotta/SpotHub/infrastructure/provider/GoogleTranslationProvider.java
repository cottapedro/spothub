package br.com.cotta.SpotHub.infrastructure.provider;

import br.com.cotta.SpotHub.domain.port.TranslationProvider;
import br.com.cotta.SpotHub.infrastructure.client.GoogleTranslateClient;
import br.com.cotta.SpotHub.interfaces.dto.in.TranslationResponse;
import br.com.cotta.SpotHub.interfaces.dto.out.TranslationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleTranslationProvider implements TranslationProvider {
    @Autowired
    private GoogleTranslateClient googleTranslateClient;
    @Value("${google-api-key}")
    private String apiKey;

    public String translate(String text, String targetLanguage) {
        TranslationRequest request = new TranslationRequest(text, targetLanguage);
        TranslationResponse response = googleTranslateClient.translate(apiKey, request);
        return response.getData().getTranslations().get(0).getTranslatedText();
    }
}
