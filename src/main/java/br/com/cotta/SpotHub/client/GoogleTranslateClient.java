package br.com.cotta.SpotHub.client;

import br.com.cotta.SpotHub.dto.in.TranslationResponse;
import br.com.cotta.SpotHub.dto.out.TranslationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleTranslateClient",
        url = "https://translation.googleapis.com")
public interface GoogleTranslateClient {

    @PostMapping(value = "/language/translate/v2", consumes = MediaType.APPLICATION_JSON_VALUE)
    TranslationResponse translate(@RequestParam("key") String apiKey, @RequestBody TranslationRequest request);
}