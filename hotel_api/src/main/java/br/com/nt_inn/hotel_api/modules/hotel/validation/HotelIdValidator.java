package br.com.nt_inn.hotel_api.modules.hotel.validation;

import br.com.nt_inn.hotel_api.config.exception.ValidationException;
import org.springframework.stereotype.Component;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class HotelIdValidator {
    public void validate(Long id) {
        if (isEmpty(id)) {
            throw new ValidationException("Id inválido!");
        }
    }
}
