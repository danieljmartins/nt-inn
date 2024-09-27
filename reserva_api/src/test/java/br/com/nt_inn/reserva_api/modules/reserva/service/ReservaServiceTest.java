package br.com.nt_inn.reserva_api.modules.reserva.service;

import br.com.nt_inn.reserva_api.modules.email.service.EmailSenderService;
import br.com.nt_inn.reserva_api.modules.hospede.model.Hospede;
import br.com.nt_inn.reserva_api.modules.hospede.repository.HospedeRepository;
import br.com.nt_inn.reserva_api.modules.hotel.client.HotelClient;
import br.com.nt_inn.reserva_api.modules.hotel.dto.QuartoDTO;
import br.com.nt_inn.reserva_api.modules.reserva.dto.ReservaRequest;
import br.com.nt_inn.reserva_api.modules.reserva.dto.ReservaResponse;
import br.com.nt_inn.reserva_api.modules.reserva.model.MetodoPagamento;
import br.com.nt_inn.reserva_api.modules.reserva.model.Reserva;
import br.com.nt_inn.reserva_api.modules.reserva.repository.ReservaRepository;
import br.com.nt_inn.reserva_api.modules.reserva.validation.ReservaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservaServiceTest {

    @InjectMocks
    private ReservaService reservaService;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private HospedeRepository hospedeRepository;

    @Mock
    private HotelClient hotelClient;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private ReservaValidator reservaValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Long idHospede = 1L;
        Long idQuarto = 1L; // Valor esperado no teste
        LocalDate checkIn = LocalDate.of(2024, 10, 1);
        LocalDate checkOut = LocalDate.of(2024, 10, 5);

        MetodoPagamento metodoPagamento = MetodoPagamento.CARTAO_CREDITO;

        ReservaRequest request = new ReservaRequest();
        request.setId_hospede(idHospede);
        request.setIdQuarto(idQuarto);
        request.setCheckIn(checkIn);
        request.setCheckOut(checkOut);
        request.setMetodo_pagamento(metodoPagamento);

        Hospede hospede = new Hospede();
        hospede.setId(idHospede);
        hospede.setNome("Teste Hóspede");

        QuartoDTO quarto = new QuartoDTO();
        quarto.setId(idQuarto);
        quarto.setDisponivel(true);
        quarto.setVlrDiaria(100.0);

        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setHospede(hospede);
        reserva.setIdQuarto(idQuarto);
        reserva.setVlrTotal(400.0);

        when(hospedeRepository.findById(idHospede)).thenReturn(Optional.of(hospede));
        when(hotelClient.getQuartoById(idQuarto)).thenReturn(quarto);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaResponse response = reservaService.save(request);

        assertNotNull(response);
        assertEquals(reserva.getId(), response.getId());
        assertEquals(quarto.getId(), response.getId());
        assertEquals(400.0, response.getVlrTotal());

        verify(emailSenderService, times(1)).enviarEmailConfirmacao(
                eq(hospede), eq(quarto), eq(400.0), eq(checkIn), eq(checkOut));

        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }
}
