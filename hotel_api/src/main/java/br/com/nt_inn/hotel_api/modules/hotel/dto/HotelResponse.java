package br.com.nt_inn.hotel_api.modules.hotel.dto;

import br.com.nt_inn.hotel_api.modules.hotel.model.Hotel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class HotelResponse {
    private Long id;
    private String nome;
    private String pais;
    private String cidade;
    private int qtdEstrelas;
    private int qtdQuartos;

    public static HotelResponse of (Hotel hotel) {
        var response = new HotelResponse();
        BeanUtils.copyProperties(hotel, response);
        return response;
    }
}
