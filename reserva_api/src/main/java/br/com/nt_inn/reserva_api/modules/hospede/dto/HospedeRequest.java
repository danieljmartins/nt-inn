package br.com.nt_inn.reserva_api.modules.hospede.dto;

import lombok.Data;

@Data
public class HospedeRequest {
    private Long id;
    private String nome;
    private String email;
}
