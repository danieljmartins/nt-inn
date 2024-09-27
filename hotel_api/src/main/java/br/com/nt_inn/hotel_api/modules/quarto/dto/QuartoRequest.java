package br.com.nt_inn.hotel_api.modules.quarto.dto;

import lombok.Data;

@Data
public class QuartoRequest {
    private Long id;
    private Long id_hotel;
    private int numero;
    private int capacidadeHospedes;
    private double vlrDiaria;
    private boolean disponivel;
}
