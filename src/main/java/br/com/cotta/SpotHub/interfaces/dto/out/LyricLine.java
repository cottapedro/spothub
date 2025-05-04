package br.com.cotta.SpotHub.interfaces.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LyricLine {
    private int timestamp;
    private String line;
    private String translation;
}
