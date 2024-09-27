package br.com.nt_inn.reserva_api.modules.reserva.dto;

import br.com.nt_inn.reserva_api.modules.hospede.dto.HospedeResponse;
import br.com.nt_inn.reserva_api.modules.hotel.dto.QuartoDTO;
import br.com.nt_inn.reserva_api.modules.reserva.model.MetodoPagamento;
import br.com.nt_inn.reserva_api.modules.reserva.model.Reserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ReservaResponse {
    private Long id;
    private QuartoDTO quarto;
    private HospedeResponse hospede;
    private MetodoPagamento metodo_pagamento;
    private double vlrTotal;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public static ReservaResponse of (Reserva reserva, QuartoDTO quarto, MetodoPagamento metodoPagamento) {
        return ReservaResponse
                .builder()
                .id(reserva.getId())
                .quarto(quarto)
                .metodo_pagamento(metodoPagamento)
                .checkIn(reserva.getCheckIn())
                .checkOut(reserva.getCheckOut())
                .vlrTotal(reserva.getVlrTotal())
                .hospede(HospedeResponse.of(reserva.getHospede()))
                .build();
    }
}
