package br.com.nt_inn.hotel_api.modules.avaliacao.service;

import br.com.nt_inn.hotel_api.config.exception.ValidationException;
import br.com.nt_inn.hotel_api.modules.avaliacao.dto.AvaliacaoRequest;
import br.com.nt_inn.hotel_api.modules.avaliacao.dto.AvaliacaoResponse;
import br.com.nt_inn.hotel_api.modules.avaliacao.model.Avaliacao;
import br.com.nt_inn.hotel_api.modules.avaliacao.repository.AvaliacaoRepository;
import br.com.nt_inn.hotel_api.modules.hotel.model.Hotel;
import br.com.nt_inn.hotel_api.modules.hotel.repository.HotelRepository;
import br.com.nt_inn.hotel_api.modules.avaliacao.validation.AvaliacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private HotelRepository hotelRepository;

    private final AvaliacaoValidator avaliacaoValidator;

    @Autowired
    public AvaliacaoService(AvaliacaoValidator avaliacaoValidator) {
        this.avaliacaoValidator = avaliacaoValidator;
    }

    // Início Método POST
    /**
     * Cada avaliação precisa ser relacionada à um hotel existente.
     * Quando o nome do avaliador não é informado, o comentário é salvo como anônimo.
     */
    public AvaliacaoResponse save(AvaliacaoRequest request) {
        avaliacaoValidator.validaInformacoesAvaliacao(request);

        Hotel hotel = hotelRepository.findById(request.getId_hotel())
                .orElseThrow(() -> new ValidationException("Hotel não encontrado!"));

        Avaliacao avaliacao = Avaliacao.of(request);
        avaliacao.setHotel(hotel);

        if (avaliacao.getNomeAvaliador() == null || avaliacao.getNomeAvaliador().isEmpty()) {
            avaliacao.setNomeAvaliador("Anônimo");
        }

        avaliacao = avaliacaoRepository.save(avaliacao);
        return AvaliacaoResponse.of(avaliacao);
    }
    // Fim Método POST

    // Início Métodos GET
    /** Busca todas as avaliações */
    public List<AvaliacaoResponse> findAll() {
        return avaliacaoRepository
                .findAll()
                .stream()
                .map(AvaliacaoResponse::of)
                .collect(Collectors.toList());
    }

    /** Busca avaliações por nota */
    public List<AvaliacaoResponse> findByNota(Double nota) {
        avaliacaoValidator.validaNota(nota);

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByNota(nota);
        verificaAvaliacoes(avaliacoes);

        return avaliacoes.stream()
                .map(AvaliacaoResponse::of)
                .collect(Collectors.toList());
    }
    // Fim Métodos GET

    private void verificaAvaliacoes(List<Avaliacao> avaliacoes) {
        if (avaliacoes.isEmpty()) {
            throw new ValidationException("Nenhuma avaliação encontrada!");
        }
    }
}
