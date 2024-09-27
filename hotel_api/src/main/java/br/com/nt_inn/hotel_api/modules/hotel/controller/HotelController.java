package br.com.nt_inn.hotel_api.modules.hotel.controller;

import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelRequest;
import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelResponse;
import br.com.nt_inn.hotel_api.modules.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // Inicío Método POST
    @PostMapping
    public HotelResponse save(@RequestBody HotelRequest request) {
        return hotelService.save(request);
    }
    // Fim Método POST

    // Inicío Métodos GET
    @GetMapping
    public List<HotelResponse> findAll() {
        return hotelService.findAll();
    }

    @GetMapping("{id}")
    public HotelResponse findById(@PathVariable Long id) {
        return hotelService.findByIdResponse(id);
    }

    @GetMapping("nome_hotel")
    public List<HotelResponse> findByNomeIgnoreCaseContaining(@RequestParam String nome) {
        return hotelService.findByNomeIgnoreCaseContaining(nome);
    }

    @GetMapping("pais_destino")
    public List<HotelResponse> findByPaisIgnoreCaseContaining(@RequestParam String pais) {
        return hotelService.findByPaisIgnoreCaseContaining(pais);
    }

    @GetMapping("cidade_destino")
    public List<HotelResponse> findByCidadeIgnoreCaseContaining(@RequestParam String cidade) {
        return hotelService.findByCidadeIgnoreCaseContaining(cidade);
    }

    @GetMapping("qtd_estrelas")
    public List<HotelResponse> findByQtdEstrelas(@RequestParam(required = false) Integer qtdEstrelas) {
        return hotelService.findByQtdEstrelas(qtdEstrelas);
    }
    // FIM Métodos GET
}
