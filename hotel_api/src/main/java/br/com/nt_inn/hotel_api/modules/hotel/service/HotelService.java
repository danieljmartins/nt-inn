package br.com.nt_inn.hotel_api.modules.hotel.service;

import br.com.nt_inn.hotel_api.config.exception.ValidationException;
import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelRequest;
import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelResponse;
import br.com.nt_inn.hotel_api.modules.hotel.model.Hotel;
import br.com.nt_inn.hotel_api.modules.hotel.repository.HotelRepository;
import br.com.nt_inn.hotel_api.modules.hotel.validation.HotelValidator;
import br.com.nt_inn.hotel_api.modules.hotel.validation.HotelIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelValidator hotelValidator;
    private final HotelIdValidator idValidator;

    public HotelResponse findByIdResponse(Long id) {
        return HotelResponse.of(findById(id));
    }

    // Início Método POST
    public HotelResponse save(HotelRequest request) {
        hotelValidator.validate(request);
        var hotel = hotelRepository.save(Hotel.of(request));
        return HotelResponse.of(hotel);
    }
    // Fim Método POST

    // Início Métodos GET
    /** Busca todos os hotéis. */
    public List<HotelResponse> findAll() {
        return hotelRepository
                .findAll()
                .stream()
                .map(HotelResponse::of)
                .collect(Collectors.toList());
    }

    /** Busca um hotel por Id. */
    public Hotel findById(Long id) {
        idValidator.validate(id);
        return hotelRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("Hotel não encontrado!"));
    }

    /** Busca hotéis pelo nome. */
    public List<HotelResponse> findByNomeIgnoreCaseContaining(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidationException("Nome do hotel não informado!");
        }

        List<Hotel> hoteis = hotelRepository.findByNomeIgnoreCaseContaining(nome);
        verificaHoteis(hoteis);

        return hoteis.stream()
                .map(HotelResponse::of)
                .collect(Collectors.toList());
    }

    /** Busca hotéis por país. */
    public List<HotelResponse> findByPaisIgnoreCaseContaining(String pais) {
        if (pais == null || pais.trim().isEmpty()) {
            throw new ValidationException("País não informado!");
        }

        List<Hotel> hoteis = hotelRepository.findByPaisIgnoreCaseContaining(pais);
        verificaHoteis(hoteis);

        return hoteis.stream()
                .map(HotelResponse::of)
                .collect(Collectors.toList());
    }

    /** Busca hotéis por cidade. */
    public List<HotelResponse> findByCidadeIgnoreCaseContaining(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new ValidationException("Cidade não informada!");
        }

        List<Hotel> hoteis = hotelRepository.findByCidadeIgnoreCaseContaining(cidade);
        verificaHoteis(hoteis);

        return hoteis.stream()
                .map(HotelResponse::of)
                .collect(Collectors.toList());
    }

    /** Busca hotéis por quantidade de estrelas. */
    public List<HotelResponse> findByQtdEstrelas(Integer qtdEstrelas) {
        if (qtdEstrelas == null) {
            throw new ValidationException("Quantidade de estrelas não informada!");
        }

        if (qtdEstrelas < 0 || qtdEstrelas > 5) {
            throw new ValidationException("Quantidade de estrelas deve ser entre 0 e 5!");
        }

        List<Hotel> hoteis = hotelRepository.findByQtdEstrelas(qtdEstrelas);
        verificaHoteis(hoteis);

        return hoteis.stream()
                .map(HotelResponse::of)
                .collect(Collectors.toList());
    }
    // Fim Métodos GET

    /** Verifica se a lista de hotéis está vazia e lança uma exceção caso esteja. */
    private void verificaHoteis(List<Hotel> hoteis) {
        if (hoteis.isEmpty()) {
            throw new ValidationException("Nenhum hotel encontrado!");
        }
    }
}
