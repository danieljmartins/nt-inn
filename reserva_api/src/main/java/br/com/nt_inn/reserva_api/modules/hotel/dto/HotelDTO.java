package br.com.nt_inn.reserva_api.modules.hotel.dto;

import lombok.Data;

@Data
public class HotelDTO {
    private Long id;
    private String nome;
    private String pais;
    private String cidade;
    private int qtdEstrelas;
    private int qtdQuartos;
}
