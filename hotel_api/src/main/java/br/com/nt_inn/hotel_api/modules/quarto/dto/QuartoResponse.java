package br.com.nt_inn.hotel_api.modules.quarto.dto;

import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelResponse;
import br.com.nt_inn.hotel_api.modules.quarto.model.Quarto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QuartoResponse {
    private Long id;
    private HotelResponse hotel;
    private int numero;
    private int capacidadeHospedes;
    private double vlrDiaria;
    private boolean disponivel;

    public static QuartoResponse of (Quarto quarto) {
        return QuartoResponse
                .builder()
                .id(quarto.getId())
                .numero(quarto.getNumero())
                .capacidadeHospedes(quarto.getCapacidadeHospedes())
                .vlrDiaria(quarto.getVlrDiaria())
                .disponivel(quarto.isDisponivel())
                .hotel(HotelResponse.of(quarto.getHotel()))
                .build();
    }
}
