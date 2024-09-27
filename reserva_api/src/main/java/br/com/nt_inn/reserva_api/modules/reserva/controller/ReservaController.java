package br.com.nt_inn.reserva_api.modules.reserva.controller;

import br.com.nt_inn.reserva_api.modules.reserva.dto.ReservaRequest;
import br.com.nt_inn.reserva_api.modules.reserva.service.ReservaService;
import br.com.nt_inn.reserva_api.modules.reserva.dto.ReservaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reserva")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    // Inicío Método POST
    @PostMapping
    public ReservaResponse save(@RequestBody ReservaRequest request) {
        return reservaService.save(request);
    }
    // Fim Método POST

    // Inicío Métodos GET
    @GetMapping
    public List<ReservaResponse> findAll() {
        return reservaService.findAll();
    }
    // Fim Métodos GET
}
