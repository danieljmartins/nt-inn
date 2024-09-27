package br.com.nt_inn.reserva_api.modules.hotel.client;

import br.com.nt_inn.reserva_api.modules.hotel.dto.QuartoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//EndPoint para rodar o projeto pelo Insmonia
//@FeignClient(name = "hotel-api", url = "http://localhost:8080/api/quarto")

// EndPoint para rodar pelo Docker
@FeignClient(name = "hotel-api", url = "http://hotel-api:8080/api/quarto")
public interface HotelClient {
    @GetMapping("/{id}")
    QuartoDTO getQuartoById(@PathVariable("id") Long id);
}