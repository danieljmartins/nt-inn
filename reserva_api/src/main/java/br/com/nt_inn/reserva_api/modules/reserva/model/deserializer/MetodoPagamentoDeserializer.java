package br.com.nt_inn.reserva_api.modules.reserva.model.deserializer;

import br.com.nt_inn.reserva_api.modules.reserva.model.MetodoPagamento;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class MetodoPagamentoDeserializer extends JsonDeserializer<MetodoPagamento> {
    @Override
    public MetodoPagamento deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getText();
        try {
            return MetodoPagamento.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IOException("Método de pagamento inválido: " + value);
        }
    }
}