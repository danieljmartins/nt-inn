package br.com.nt_inn.reserva_api.modules.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuartoDTO {
    private Long id;
    private int numero;
    private int capacidadeHospedes;
    private double vlrDiaria;
    private boolean disponivel;
    private HotelDTO hotel;
}
