package br.com.nt_inn.reserva_api.modules.reserva.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MetodoPagamento {

    PIX("pix"),
    CARTAO_CREDITO("cartão de crédito"),
    BOLETO("boleto");

    private final String descricao;

    MetodoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static MetodoPagamento fromString(String metodo) {
        for (MetodoPagamento mp : MetodoPagamento.values()) {
            if (mp.descricao.equalsIgnoreCase(metodo)) {
                return mp;
            }
        }
        throw new IllegalArgumentException("Método de pagamento inválido: " + metodo);
    }
}