package br.com.nt_inn.hotel_api.modules.avaliacao.model;

import br.com.nt_inn.hotel_api.modules.avaliacao.dto.AvaliacaoRequest;
import br.com.nt_inn.hotel_api.modules.hotel.model.Hotel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avaliacao")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;

    @Column(name = "nome_avaliador", nullable = true)
    private String nomeAvaliador;

    @Column(name = "nota", nullable = false)
    private double nota;

    @Column(name = "comentario_avaliacao", nullable = true)
    private String comentarioAvaliacao;

    public static Avaliacao of(AvaliacaoRequest request) {
        var avaliacao = new Avaliacao();
        BeanUtils.copyProperties(request, avaliacao);
        return avaliacao;
    }
}
