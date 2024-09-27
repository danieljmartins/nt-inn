package br.com.nt_inn.reserva_api.modules.hospede.repository;

import br.com.nt_inn.reserva_api.modules.hospede.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospedeRepository extends JpaRepository<Hospede, Long> {
    List<Hospede> findByNomeIgnoreCaseContaining(String nome);
    List<Hospede> findByEmailIgnoreCaseContaining(String email);
}
