package br.com.nt_inn.reserva_api.modules.hospede.factory;

import br.com.nt_inn.reserva_api.modules.hospede.dto.HospedeResponse;
import br.com.nt_inn.reserva_api.modules.hospede.model.Hospede;
import org.springframework.stereotype.Component;

@Component
public class HospedeResponseFactory {

    public HospedeResponse criarHospedeResponse(Hospede hospede) {
        return HospedeResponse.of(hospede);
    }
}
