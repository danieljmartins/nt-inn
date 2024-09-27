package br.com.nt_inn.hotel_api.modules.quarto.model;

import br.com.nt_inn.hotel_api.modules.hotel.model.Hotel;
import br.com.nt_inn.hotel_api.modules.quarto.dto.QuartoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quarto")
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;

    @Column(name = "numero", nullable = false)
    private int numero;

    @Column(name = "capacidade_hospedes", nullable = false)
    private int capacidadeHospedes;

    @Column(name = "vlr_diaria", nullable = false)
    private double vlrDiaria;

    @Column(name = "disponivel", nullable = false)
    private boolean disponivel;

    public static Quarto of(QuartoRequest request) {
        var quarto = new Quarto();
        BeanUtils.copyProperties(request, quarto);
        return quarto;
    }
}
