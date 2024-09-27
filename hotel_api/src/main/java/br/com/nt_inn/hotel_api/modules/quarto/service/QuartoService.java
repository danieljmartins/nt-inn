package br.com.nt_inn.hotel_api.modules.quarto.service;

import br.com.nt_inn.hotel_api.config.exception.ValidationException;
import br.com.nt_inn.hotel_api.modules.hotel.model.Hotel;
import br.com.nt_inn.hotel_api.modules.quarto.dto.QuartoRequest;
import br.com.nt_inn.hotel_api.modules.quarto.dto.QuartoResponse;
import br.com.nt_inn.hotel_api.modules.quarto.model.Quarto;
import br.com.nt_inn.hotel_api.modules.quarto.repository.QuartoRepository;
import br.com.nt_inn.hotel_api.modules.quarto.validation.QuartoIdValidator;
import br.com.nt_inn.hotel_api.modules.quarto.validation.QuartoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nt_inn.hotel_api.modules.hotel.repository.HotelRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private HotelRepository hotelRepository;

    private final QuartoValidator quartoValidator;
    private final QuartoIdValidator quartoIdValidator;

    @Autowired
    public QuartoService(QuartoValidator quartoValidator, QuartoIdValidator quartoIdValidator) {
        this.quartoValidator = quartoValidator;
        this.quartoIdValidator = quartoIdValidator;
    }

    public QuartoResponse findByIdResponse(Long id) {
        return QuartoResponse.of(findById(id));
    }

    // Início Método POST
    public QuartoResponse save(QuartoRequest request) {
        quartoValidator.validaInformacoesQuarto(request);

        Hotel hotel = hotelRepository.findById(request.getId_hotel())
                .orElseThrow(() -> new ValidationException("Hotel não encontrado!"));

        Quarto quarto = Quarto.of(request);
        quarto.setHotel(hotel);

        quarto = quartoRepository.save(quarto);
        return QuartoResponse.of(quarto);
    }
    // Fim Método POST

    // Início Métodos GET
    /** Busca todos os quartos */
    public List<QuartoResponse> findAll() {
        return quartoRepository
                .findAll()
                .stream()
                .map(QuartoResponse::of)
                .collect(Collectors.toList());
    }

    /** Busca um quarto por Id */
    public Quarto findById(Long id) {
        quartoIdValidator.validaIdInformado(id);
        return quartoRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("Quarto não encontrado!"));
    }

    /** Busca quartos por hotel */
    public List<QuartoResponse> findByHotelId(Long idHotel) {
        quartoIdValidator.validaIdHotel(idHotel);

        List<Quarto> quartos = quartoRepository.findByHotelId(idHotel);
        verificaQuartos(quartos);

        return quartos.stream()
                .map(QuartoResponse::of)
                .collect(Collectors.toList());
    }

    /** Busca quartos por capacidade de hóspedes */
    public List<QuartoResponse> findByCapacidadeHospedes(Integer capacidadeHospedes) {
        quartoValidator.validaCapacidadeHospedes(capacidadeHospedes);

        List<Quarto> quartos = quartoRepository.findByCapacidadeHospedes(capacidadeHospedes);
        verificaQuartos(quartos);

        return quartos.stream()
                .map(QuartoResponse::of)
                .collect(Collectors.toList());
    }

    /** Busca quartos por valor da diária */
    public List<QuartoResponse> findByVlrDiariaBetween(Double vlrDiariaMin, Double vlrDiariaMax) {
        quartoValidator.validaValoresDiaria(vlrDiariaMin, vlrDiariaMax);

        List<Quarto> quartos = quartoRepository.findByVlrDiariaBetween(vlrDiariaMin, vlrDiariaMax);
        verificaQuartos(quartos);

        return quartos.stream()
                .map(QuartoResponse::of)
                .collect(Collectors.toList());
    }
    // Fim Métodos GET

    private void verificaQuartos(List<Quarto> quartos) {
        if (quartos.isEmpty()) {
            throw new ValidationException("Nenhum quarto encontrado!");
        }
    }
}