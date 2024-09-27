package br.com.nt_inn.reserva_api.modules.reserva.validation;

import br.com.nt_inn.reserva_api.config.exception.ValidationException;
import br.com.nt_inn.reserva_api.modules.reserva.dto.ReservaRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class ReservaValidator {

    public void validaInformacoesReserva(ReservaRequest request) {
        if (isEmpty(request.getMetodo_pagamento())) {
            throw new ValidationException("Método de pagamento não informado!");
        }

        LocalDate hoje = LocalDate.now();

        if (request.getCheckIn().isBefore(hoje)) {
            throw new ValidationException("A data de check-in não pode ser anterior à data atual!");
        }

        if (request.getCheckOut().isBefore(hoje)) {
            throw new ValidationException("A data de check-out não pode ser anterior à data atual!");
        }

        if (request.getCheckIn().isAfter(request.getCheckOut())) {
            throw new ValidationException("A data de check-out não pode ser anterior à data de check-in!");
        }
    }
}
