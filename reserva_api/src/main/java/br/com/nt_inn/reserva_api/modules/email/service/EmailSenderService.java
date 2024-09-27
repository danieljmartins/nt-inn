package br.com.nt_inn.reserva_api.modules.email.service;

import br.com.nt_inn.reserva_api.config.exception.ValidationException;
import br.com.nt_inn.reserva_api.modules.hospede.model.Hospede;
import br.com.nt_inn.reserva_api.modules.hotel.dto.QuartoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmailSenderService {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

    @Autowired
    private EmailService emailService;

    public void enviarEmailConfirmacao(Hospede hospede, QuartoDTO quarto, double vlrTotal, LocalDate checkIn, LocalDate checkOut) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String checkInFormatado = checkIn.format(formatter);
        String checkOutFormatado = checkOut.format(formatter);

        String assunto = "Confirmação de Reserva";
        String mensagem = "Sua reserva no hotel " + quarto.getHotel().getNome() + " foi confirmada para as datas de " +
                checkInFormatado + " a " + checkOutFormatado + ". O valor total da reserva é R$ " + vlrTotal + ".";

        try {
            logger.info("Enviando email para o endereco {}: ", hospede.getEmail());
            emailService.enviaEmail(hospede.getEmail(), assunto, mensagem);
        } catch (IOException e) {
            logger.error("Erro: Erro ao enviar o e-mail de confirmação!");
            throw new ValidationException("Erro ao enviar o e-mail de confirmação!");
        }
    }
}
