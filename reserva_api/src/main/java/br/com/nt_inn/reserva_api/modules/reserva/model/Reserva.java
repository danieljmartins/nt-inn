package br.com.nt_inn.reserva_api.modules.reserva.model;

import br.com.nt_inn.reserva_api.modules.hospede.model.Hospede;
import br.com.nt_inn.reserva_api.modules.reserva.dto.ReservaRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "id_quarto", nullable = false)
    private Long idQuarto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hospede", nullable = false)
    private Hospede hospede;

    @Column(name = "metodo_pagamento", nullable = false)
    private MetodoPagamento metodo_pagamento;

    @Setter
    @Column(name = "vlr_total", nullable = false)
    private double vlrTotal;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    public static Reserva of(ReservaRequest request) {
        var reserva = new Reserva();
        BeanUtils.copyProperties(request, reserva);
        return reserva;
    }

}
