package com.egg.noticias.controladores;

import com.egg.noticias.servicios.NoticiaServicio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class InicioControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;
    DateFormat dateFormat = new SimpleDateFormat("EEEEEEEEEE, d MMM yyyy");

    
    @GetMapping("/")

    public String home(ModelMap model) {

        String date = dateFormat.format(new Date());
        model.addAttribute("fecha", date);
        model.addAttribute("noticias", noticiaServicio.listarNoticias());

        return "mostrar";
    }

    @GetMapping("/show")
    public String show(ModelMap model) {
        model.addAttribute("noticias", noticiaServicio.listarNoticias());
        return "show";
    }
}
