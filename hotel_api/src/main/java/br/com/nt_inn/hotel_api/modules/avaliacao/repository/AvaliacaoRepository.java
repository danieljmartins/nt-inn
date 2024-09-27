package br.com.nt_inn.hotel_api.modules.avaliacao.repository;

import br.com.nt_inn.hotel_api.modules.avaliacao.model.Avaliacao;
import br.com.nt_inn.hotel_api.modules.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    List<Avaliacao> findByNota(Double nota);
}
