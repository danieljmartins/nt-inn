package br.com.nt_inn.reserva_api.modules.reserva.service;

import br.com.nt_inn.reserva_api.config.exception.ValidationException;
import br.com.nt_inn.reserva_api.modules.email.service.EmailSenderService;
import br.com.nt_inn.reserva_api.modules.hospede.model.Hospede;
import br.com.nt_inn.reserva_api.modules.hospede.repository.HospedeRepository;
import br.com.nt_inn.reserva_api.modules.hotel.client.HotelClient;
import br.com.nt_inn.reserva_api.modules.hotel.dto.QuartoDTO;
import br.com.nt_inn.reserva_api.modules.reserva.dto.ReservaRequest;
import br.com.nt_inn.reserva_api.modules.reserva.dto.ReservaResponse;
import br.com.nt_inn.reserva_api.modules.reserva.model.Reserva;
import br.com.nt_inn.reserva_api.modules.reserva.repository.ReservaRepository;
import br.com.nt_inn.reserva_api.modules.reserva.validation.ReservaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private static final Logger logger = LoggerFactory.getLogger(ReservaService.class);

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private HotelClient hotelClient;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ReservaValidator reservaValidator;

    // Início Método POST
    public ReservaResponse save(ReservaRequest request) {
        logger.info("Iniciando o processo de reserva para o hóspede ID: {}", request.getId_hospede());

        reservaValidator.validaInformacoesReserva(request);

        logger.info("ReservaService: consultando WebService hotel_api");
        QuartoDTO quarto = hotelClient.getQuartoById(request.getIdQuarto());
        if (quarto == null) {
            throw new ValidationException("Quarto não encontrado!");
        }

        if (!quarto.isDisponivel()) {
            throw new ValidationException("Quarto indisponível!");
        }

        if (!verificaDisponibilidadeQuarto(request.getIdQuarto(), request.getCheckIn(), request.getCheckOut())) {
            throw new ValidationException("O quarto não está disponível para as datas informadas!");
        }

        long diasReservados = ChronoUnit.DAYS.between(request.getCheckIn(), request.getCheckOut());
        if (diasReservados == 0) {
            diasReservados = 1;
        }

        double vlrTotal = diasReservados * quarto.getVlrDiaria();

        Hospede hospede = hospedeRepository.findById(request.getId_hospede())
                .orElseThrow(() -> new ValidationException("Hóspede não encontrado!"));

        Reserva reserva = Reserva.of(request);
        reserva.setHospede(hospede);
        reserva.setVlrTotal(vlrTotal);

        reserva = reservaRepository.save(reserva);
        logger.info("Reserva ID: {} criada com sucesso!", reserva.getId());

        // Envio de e-mail utilizando a classe de serviço separada
        emailSenderService.enviarEmailConfirmacao(hospede, quarto, vlrTotal, request.getCheckIn(), request.getCheckOut());

        return ReservaResponse.of(reserva, quarto, request.getMetodo_pagamento());
    }

    // Início Métodos GET
    public List<ReservaResponse> findAll() {
        return reservaRepository
                .findAll()
                .stream()
                .map(reserva -> {
                    QuartoDTO quarto = hotelClient.getQuartoById(reserva.getIdQuarto());
                    return ReservaResponse.of(reserva, quarto, reserva.getMetodo_pagamento());
                })
                .collect(Collectors.toList());
    }
    // Fim Métodos GET

    private boolean verificaDisponibilidadeQuarto(Long idQuarto, LocalDate checkIn, LocalDate checkOut) {
        List<Reserva> reservasExistentes = reservaRepository.findByIdQuarto(idQuarto);
        for (Reserva reserva : reservasExistentes) {
            if (checkIn.isBefore(reserva.getCheckOut()) && checkOut.isAfter(reserva.getCheckIn())) {
                return false;
            }
        }
        return true;
    }
}
