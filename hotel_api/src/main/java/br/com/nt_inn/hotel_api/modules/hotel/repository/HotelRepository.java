package br.com.nt_inn.hotel_api.modules.hotel.repository;

import br.com.nt_inn.hotel_api.modules.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNomeIgnoreCaseContaining(String nome);
    List<Hotel> findByPaisIgnoreCaseContaining(String pais);
    List<Hotel> findByCidadeIgnoreCaseContaining(String cidade);
    List<Hotel> findByQtdEstrelas(Integer qtdEstrelas);
}
