package br.com.nt_inn.hotel_api.modules.avaliacao.validation;

import br.com.nt_inn.hotel_api.config.exception.ValidationException;
import br.com.nt_inn.hotel_api.modules.avaliacao.dto.AvaliacaoRequest;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class AvaliacaoValidator {
    public void validaInformacoesAvaliacao(AvaliacaoRequest request) {
        if (request.getId_hotel() == null || request.getId_hotel() <= 0) {
            throw new ValidationException("Hotel não informado!");
        }
        validaNota(request.getNota());
    }

    public void validaNota(Double nota) {
        if (isEmpty(nota)) {
            throw new ValidationException("Informe a nota!");
        }

        if (nota < 0 || nota > 5) {
            throw new ValidationException("As notas vão de 0 a 5!");
        }
    }
}
