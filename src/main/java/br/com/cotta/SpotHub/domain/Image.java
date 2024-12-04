package br.com.cotta.SpotHub.domain;

import lombok.Data;

@Data
public class Image {
    private String url;
    private Integer height;
    private Integer width;
}