package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.excepciones.MiExcepcion;
import com.egg.noticias.servicios.NoticiaServicio;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    private final Logger LOGGER = LoggerFactory.getLogger(NoticiaControlador.class);

    @GetMapping("/cargar")
    public String noticia(ModelMap model) {
        return "noticiaForm";
    }

    @PostMapping("/carga")
    public String cargarNoticia(@RequestParam String titulo, @RequestParam String cuerpo, @RequestParam String bajada, @RequestParam(required = false) String imagen, ModelMap model) throws MiExcepcion {
        try {
            List<Noticia> noticias = noticiaServicio.listarNoticias();
            model.put("noticias", noticias);
            noticiaServicio.crearNoticia(titulo, cuerpo, imagen, bajada);
//            Noticia noticia = noticiaServicio.crearNoticia(titulo, cuerpo, imagen);
            model.put("exito", "La Noticia fue cargada exitosamente");
//            LOGGER.info("la noticia creada es: {}", noticia);
        } catch (MiExcepcion e) {
            model.put("error", e.getMessage());
//            LOGGER.info("error: {}", e.getMessage());
//            
            return "noticiaForm";
        }
        return "mostrar";

    }

    @GetMapping("")
    public String noticiaLeer(ModelMap model) {
        model.addAttribute("noticias", noticiaServicio.listarNoticias());
        return "noticia";
    }
}
