package br.com.nt_inn.reserva_api.modules.hospede.controller;

import br.com.nt_inn.reserva_api.modules.hospede.dto.HospedeRequest;
import br.com.nt_inn.reserva_api.modules.hospede.dto.HospedeResponse;
import br.com.nt_inn.reserva_api.modules.hospede.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospede")
public class HospedeController {

    @Autowired
    private HospedeService hospedeService;

    // Inicío Método POST
    @PostMapping
    public HospedeResponse save(@RequestBody HospedeRequest request) {
        return hospedeService.save(request);
    }
    // Fim Método POST

    // Inicío Métodos GET
    @GetMapping
    public List<HospedeResponse> findAll() {
        return hospedeService.findAll();
    }

    @GetMapping("nome")
    public List<HospedeResponse> findByNomeIgnoreCaseContaining(@RequestParam String nome) {
        return hospedeService.findByNomeIgnoreCaseContaining(nome);
    }

    @GetMapping("email")
    public List<HospedeResponse> findByEmailIgnoreCaseContaining(@RequestParam String email) {
        return hospedeService.findByEmailIgnoreCaseContaining(email);
    }
    // Fim Métodos GET
}
