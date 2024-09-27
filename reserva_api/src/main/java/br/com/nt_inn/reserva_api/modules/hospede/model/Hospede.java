package br.com.nt_inn.reserva_api.modules.hospede.model;

import br.com.nt_inn.reserva_api.modules.hospede.dto.HospedeRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospede")
public class Hospede {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    public static Hospede of(HospedeRequest request) {
        var hospede = new Hospede();
        BeanUtils.copyProperties(request, hospede);
        return hospede;
    }
}
