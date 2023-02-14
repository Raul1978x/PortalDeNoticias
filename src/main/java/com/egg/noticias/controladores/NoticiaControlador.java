package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.servicios.NoticiaServicio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {

    @Autowired
    public NoticiaServicio noticiaServicio;
    DateFormat dateFormat = new SimpleDateFormat("EEEEEEEEEE, d MMM yyyy");

    @GetMapping("/mostrar/{id}")
    public String noticiaLeer(@PathVariable String id, ModelMap model) {
        mostrarFecha(model);
//        List<Noticia> noticias = new ArrayList<>();

        Noticia noticia = noticiaServicio.buscarNoticiaPorId(id);

        model.put("noticia", noticia);

        return "noticia";
    }

    private void mostrarFecha(ModelMap model) {
        String date = dateFormat.format(new Date());
        model.addAttribute("fecha", date);
    }
}
