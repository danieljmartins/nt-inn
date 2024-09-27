package br.com.nt_inn.hotel_api.modules.quarto.controller;

import br.com.nt_inn.hotel_api.modules.quarto.dto.QuartoRequest;
import br.com.nt_inn.hotel_api.modules.quarto.dto.QuartoResponse;
import br.com.nt_inn.hotel_api.modules.quarto.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quarto")
public class QuartoController {
    @Autowired
    private QuartoService quartoService;

    // Início Método POST
    @PostMapping
    public QuartoResponse save(@RequestBody QuartoRequest request) {
        return quartoService.save(request);
    }
    // Fim Método POST

    // Início Métodos GET
    @GetMapping
    public List<QuartoResponse> findAll() {
        return quartoService.findAll();
    }

    @GetMapping("{id}")
    public QuartoResponse findById(@PathVariable Long id) {
        return quartoService.findByIdResponse(id);
    }

    @GetMapping("hotel/{idHotel}")
    public List<QuartoResponse> findByHotelId(@PathVariable Long idHotel) {
        return quartoService.findByHotelId(idHotel);
    }

    @GetMapping("capacidade_hospedes")
    public List<QuartoResponse> findByCapacidadeHospedes(@RequestParam(required = false) Integer capacidadeHospedes) {
        return quartoService.findByCapacidadeHospedes(capacidadeHospedes);
    }

    @GetMapping("valor_diaria")
    public List<QuartoResponse> findByVlrDiariaBetween(
            @RequestParam(required = false) Double vlrDiariaMin,
            @RequestParam(required = false) Double vlrDiariaMax) {
        return quartoService.findByVlrDiariaBetween(vlrDiariaMin, vlrDiariaMax);
    }
    // Fim Métodos GET
}
