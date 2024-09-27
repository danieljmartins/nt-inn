package br.com.nt_inn.reserva_api.modules.reserva.dto;

import br.com.nt_inn.reserva_api.modules.reserva.model.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    private StatusReserva statusReserva;
}
