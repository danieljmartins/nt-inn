package br.com.nt_inn.hotel_api.modules.quarto.validation;

import br.com.nt_inn.hotel_api.config.exception.ValidationException;
import br.com.nt_inn.hotel_api.modules.quarto.dto.QuartoRequest;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class QuartoValidator {

    public void validaInformacoesQuarto(QuartoRequest request) {
        if (request.getId_hotel() == null || request.getId_hotel() <= 0) {
            throw new ValidationException("Hotel não informado!");
        }
        if (request.getNumero() < 0) {
            throw new ValidationException("Número do quarto não informado!");
        }
        if (request.getCapacidadeHospedes() <= 0) {
            throw new ValidationException("Capacidade de hóspedes não informada!");
        }
        if (request.getVlrDiaria() <= 0.00) {
            throw new ValidationException("Valor da diária não informado!");
        }
        if (isEmpty(request.isDisponivel())) {
            throw new ValidationException("Disponibilidade não informada!");
        }
    }

    public void validaCapacidadeHospedes(Integer capacidadeHospedes) {
        if (capacidadeHospedes == null) {
            throw new ValidationException("Quantidade de hóspedes não informada!");
        }

        if (capacidadeHospedes <= 0) {
            throw new ValidationException("A capacidade de hóspedes deve ser maior que 0!");
        }
    }

    public void validaValoresDiaria(Double vlrDiariaMin, Double vlrDiariaMax) {
        if (vlrDiariaMin == null || vlrDiariaMax == null) {
            throw new ValidationException("Informe os valores mínimo e máximo para a diária!");
        }

        if (vlrDiariaMin <= 0 || vlrDiariaMax <= 0) {
            throw new ValidationException("Os valores devem ser maiores que 0!");
        }

        if (vlrDiariaMin > vlrDiariaMax) {
            throw new ValidationException("O valor mínimo não pode ser maior que o valor máximo!");
        }
    }
}
