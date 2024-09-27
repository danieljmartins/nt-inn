package br.com.nt_inn.reserva_api.modules.email.service;

import br.com.nt_inn.reserva_api.modules.reserva.service.ReservaService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(ReservaService.class);

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.sender-email}")
    private String emailRemetente;

    public void enviaEmail(String emailDestinatario, String assunto, String mensagem) throws IOException {
        Email from = new Email(emailRemetente);
        Email to = new Email(emailDestinatario);
        Content content = new Content("text/plain", mensagem);
        Mail mail = new Mail(from, assunto, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            // Logs
            System.out.println(response.getHeaders());
            logger.info("Email enviado com sucesso para o endereço: {}", emailDestinatario);
            logger.info("StatusCode: {}", response.getStatusCode());
            logger.info("getHeaders: {}", response.getHeaders());
            logger.info("getBody: {}", response.getBody());
        } catch (IOException e) {
            logger.error("Erro: Erro ao enviar o e-mail de confirmação (classe EmailService)");
            throw e;
        }
    }
}