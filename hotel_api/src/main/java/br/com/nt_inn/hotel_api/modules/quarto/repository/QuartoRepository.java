package br.com.nt_inn.hotel_api.modules.quarto.repository;

import br.com.nt_inn.hotel_api.modules.quarto.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    List<Quarto> findByHotelId(Long idHotel);
    List<Quarto> findByCapacidadeHospedes(Integer capacidadeHospedes);
    List<Quarto> findByVlrDiariaBetween(Double vlrDiariaMin, Double vlrDiariaMax);
}
