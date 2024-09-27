package br.com.nt_inn.reserva_api.modules.hospede.service;

import br.com.nt_inn.reserva_api.modules.hospede.dto.HospedeRequest;
import br.com.nt_inn.reserva_api.config.exception.ValidationException;
import br.com.nt_inn.reserva_api.modules.hospede.dto.HospedeResponse;
import br.com.nt_inn.reserva_api.modules.hospede.factory.HospedeResponseFactory;
import br.com.nt_inn.reserva_api.modules.hospede.model.Hospede;
import br.com.nt_inn.reserva_api.modules.hospede.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private HospedeResponseFactory hospedeResponseFactory;

    // Início Método POST
    public HospedeResponse save(HospedeRequest request) {
        validaInformacoesHospede(request);
        var hospede = hospedeRepository.save(Hospede.of(request));
        return hospedeResponseFactory.criarHospedeResponse(hospede);
    }
    // Fim Método POST

    // Início Métodos GET
    /** Busca todos os hóspedes */
    public List<HospedeResponse> findAll() {
        return hospedeRepository
                .findAll()
                .stream()
                .map(hospedeResponseFactory::criarHospedeResponse)
                .collect(Collectors.toList());
    }

    /** Busca hóspedes pelo nome */
    public List<HospedeResponse> findByNomeIgnoreCaseContaining(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidationException("Nome do hóspede não informado!");
        }

        List<Hospede> hospedes = hospedeRepository.findByNomeIgnoreCaseContaining(nome);
        verificaHospedes(hospedes);

        return hospedes.stream()
                .map(hospedeResponseFactory::criarHospedeResponse)
                .collect(Collectors.toList());
    }

    /** Busca hóspedes pelo email */
    public List<HospedeResponse> findByEmailIgnoreCaseContaining(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email não informado!");
        }

        List<Hospede> hospedes = hospedeRepository.findByEmailIgnoreCaseContaining(email);
        verificaHospedes(hospedes);

        return hospedes.stream()
                .map(hospedeResponseFactory::criarHospedeResponse)
                .collect(Collectors.toList());
    }
    // Fim Métodos GET

    private void validaInformacoesHospede(HospedeRequest request) {
        if (isEmpty(request.getNome())){
            throw new ValidationException("Nome do hóspede não informado!");
        }
        if (isEmpty(request.getEmail())){
            throw new ValidationException("Email não informado!");
        }
    }

    private void verificaHospedes(List<Hospede> hospedes) {
        if (hospedes.isEmpty()) {
            throw new ValidationException("Nenhum hóspede encontrado!");
        }
    }
}
