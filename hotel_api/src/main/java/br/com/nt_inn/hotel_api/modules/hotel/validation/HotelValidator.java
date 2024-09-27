package br.com.nt_inn.hotel_api.modules.hotel.validation;

import br.com.nt_inn.hotel_api.config.exception.ValidationException;
import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelRequest;
import org.springframework.stereotype.Component;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class HotelValidator {
    public void validate(HotelRequest request) {
        if (isEmpty(request.getNome())) {
            throw new ValidationException("Nome do hotel não informado!");
        }
        if (isEmpty(request.getPais())) {
            throw new ValidationException("País de origem do hotel não informado!");
        }
        if (isEmpty(request.getCidade())) {
            throw new ValidationException("Cidade de origem do hotel não informada!");
        }
        if (request.getQtdEstrelas() < 0 || request.getQtdEstrelas() > 5) {
            throw new ValidationException("É necessário informar de 0 a 5 estrelas para cada hotel");
        }
        if (request.getQtdQuartos() <= 0) {
            throw new ValidationException("Quantidade de quartos do hotel não informada!");
        }
    }
}
