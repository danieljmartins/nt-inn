package br.com.nt_inn.hotel_api.modules.quarto.validation;

import br.com.nt_inn.hotel_api.config.exception.ValidationException;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class QuartoIdValidator {

    public void validaIdInformado(Long id) {
        if (isEmpty(id)) {
            throw new ValidationException("Id inválido!");
        }
    }

    public void validaIdHotel(Long idHotel) {
        if (idHotel == null) {
            throw new ValidationException("Hotel não informado!");
        }

        if (idHotel <= 0) {
            throw new ValidationException("Hotel inválido!");
        }
    }
}
