package com.ingenieriaSoftware.videojuegos.controller;

import com.ingenieriaSoftware.videojuegos.business.domain.Videojuego;
import com.ingenieriaSoftware.videojuegos.business.logic.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class InicioController {

    @Autowired
    private VideojuegoService videojuegoService;


    @GetMapping("/inicio")
    public String inicio(Model model) {
        try {
            Collection<Videojuego> colVideo = videojuegoService.listarVideojuegos();
            model.addAttribute("videojuegos", colVideo);
            return "view/inicio";
        } catch (Exception ex) {
            return "error";
        }
    }

}
