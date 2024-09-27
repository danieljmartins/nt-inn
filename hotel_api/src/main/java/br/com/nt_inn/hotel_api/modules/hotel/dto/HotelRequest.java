package br.com.nt_inn.hotel_api.modules.hotel.dto;

import lombok.Data;

@Data
public class HotelRequest {
    private Long id;
    private String nome;
    private String pais;
    private String cidade;
    private int qtdEstrelas;
    private int qtdQuartos;
}
