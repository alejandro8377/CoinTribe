package com.cointribe.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Inicio", description = "Página principal de CoinTribe")
public class HomeController {

    @GetMapping("/")
    @Operation(summary = "Abrir página principal")
    @ApiResponse(responseCode = "200", description = "Página principal")
    public String index() {
        return "forward:/index.html";
    }
}
