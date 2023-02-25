package com.egg.noticias.controladores;

import com.egg.noticias.excepciones.MiExcepcion;
import com.egg.noticias.servicios.NoticiaServicio;
import com.egg.noticias.servicios.UsuarioServicio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class InicioControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;
    DateFormat dateFormat = new SimpleDateFormat("EEEEEEEEEE, d MMM yyyy");

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("")
    public String home(HttpSession session, ModelMap model) {
        mostrarFecha(model);
        model.addAttribute("noticias", noticiaServicio.listarNoticias());
        return "mostrar";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        mostrarFecha(model);
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, 
            @RequestParam String email, @RequestParam String password, 
            @RequestParam String password2, ModelMap model) {
        mostrarFecha(model);
        try {
            usuarioServicio.registrar(nombre, email, password, password2);
            model.put("exito", "Usuario Registrado");

            return "redirect:/";

        } catch (MiExcepcion ex) {
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            model.put("email", email);

            return "registro";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {
        mostrarFecha(model);
        if (error  != null) {
            model.put("error", "Usuario o contraseña incorrecto!!");
        }
        return "login";
    }
    
    private void mostrarFecha(ModelMap model){
        String date = dateFormat.format(new Date());
        model.addAttribute("fecha", date);
    }
}
