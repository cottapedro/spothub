package br.com.cotta.SpotHub.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TranslationRequest {
    private String q;
    private String target;
}