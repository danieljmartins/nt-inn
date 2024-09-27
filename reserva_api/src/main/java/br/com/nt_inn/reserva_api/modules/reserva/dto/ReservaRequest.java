package br.com.nt_inn.reserva_api.modules.reserva.dto;

import br.com.nt_inn.reserva_api.modules.reserva.model.MetodoPagamento;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRequest {
    private Long id;
    private Long idQuarto;
    private Long id_hospede;
    private MetodoPagamento metodo_pagamento;
    private double vlrTotal;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
