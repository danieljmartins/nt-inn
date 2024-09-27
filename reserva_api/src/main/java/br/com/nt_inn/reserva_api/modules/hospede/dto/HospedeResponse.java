package br.com.nt_inn.reserva_api.modules.hospede.dto;

import br.com.nt_inn.reserva_api.modules.hospede.model.Hospede;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class HospedeResponse {
    private Long id;
    private String nome;
    private String email;

    public static HospedeResponse of (Hospede hospede) {
        var response = new HospedeResponse();
        BeanUtils.copyProperties(hospede, response);
        return response;
    }
}
