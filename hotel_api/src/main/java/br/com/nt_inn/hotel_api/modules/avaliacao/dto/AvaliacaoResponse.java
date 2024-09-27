package br.com.nt_inn.hotel_api.modules.avaliacao.dto;

import br.com.nt_inn.hotel_api.modules.avaliacao.model.Avaliacao;
import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AvaliacaoResponse {
    private Long id;
    private HotelResponse hotel;
    private String nomeAvaliador;
    private double nota;
    private String comentarioAvaliacao;

    public static AvaliacaoResponse of (Avaliacao avaliacao) {
        return AvaliacaoResponse
                .builder()
                .id(avaliacao.getId())
                .nomeAvaliador(avaliacao.getNomeAvaliador())
                .nota(avaliacao.getNota())
                .comentarioAvaliacao(avaliacao.getComentarioAvaliacao())
                .hotel(HotelResponse.of(avaliacao.getHotel()))
                .build();
    }
}
