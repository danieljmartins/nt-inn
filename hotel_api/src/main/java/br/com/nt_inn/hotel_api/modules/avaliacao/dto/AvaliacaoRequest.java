package br.com.nt_inn.hotel_api.modules.avaliacao.dto;

import lombok.Data;

@Data
public class AvaliacaoRequest {
    private Long id;
    private Long id_hotel;
    private String nomeAvaliador;
    private double nota;
    private String comentarioAvaliacao;
}
