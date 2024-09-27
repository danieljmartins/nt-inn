package br.com.nt_inn.hotel_api.modules.avaliacao.controller;

import br.com.nt_inn.hotel_api.modules.avaliacao.dto.AvaliacaoRequest;
import br.com.nt_inn.hotel_api.modules.avaliacao.dto.AvaliacaoResponse;
import br.com.nt_inn.hotel_api.modules.avaliacao.service.AvaliacaoService;
import br.com.nt_inn.hotel_api.modules.hotel.dto.HotelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoController {
    @Autowired
    private AvaliacaoService avaliacaoService;

    // Inicío Método POST
    @PostMapping
    public AvaliacaoResponse save(@RequestBody AvaliacaoRequest request) {
        return avaliacaoService.save(request);
    }
    // Fim Método POST

    // Inicío Métodos GET
    @GetMapping
    public List<AvaliacaoResponse> findAll() {
        return avaliacaoService.findAll();
    }

    @GetMapping("nota")
    public List<AvaliacaoResponse> findByNota(@RequestParam(required = false) Double nota) {
        return avaliacaoService.findByNota(nota);
    }
    // Fim Métodos GET
}
