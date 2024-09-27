package br.com.nt_inn.hotel_api.modules.hotel.model;

import br.com.nt_inn.hotel_api.modules.avaliacao.model.Avaliacao;
import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelRequest;
import br.com.nt_inn.hotel_api.modules.quarto.model.Quarto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "pais", nullable = false)
    private String pais;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "qtd_estrelas", nullable = false)
    private int qtdEstrelas;

    @Column(name = "qtd_quartos", nullable = true)
    private int qtdQuartos;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quarto> quartos;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    public static Hotel of(HotelRequest request) {
        var hotel = new Hotel();
        BeanUtils.copyProperties(request, hotel);
        return hotel;
    }
}
