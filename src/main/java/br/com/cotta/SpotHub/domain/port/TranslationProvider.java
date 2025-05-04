package br.com.cotta.SpotHub.domain.port;

public interface TranslationProvider {
    String translate(String text, String targetLanguage);
}
