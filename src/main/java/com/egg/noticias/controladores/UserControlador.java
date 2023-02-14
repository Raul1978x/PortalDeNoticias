package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.servicios.NoticiaServicio;
import com.egg.noticias.servicios.UsuarioServicio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Raúl Gómez
 */
@Controller
@RequestMapping("/user")
public class UserControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private NoticiaServicio noticiaServicio;
    DateFormat dateFormat = new SimpleDateFormat("EEEEEEEEEE, d MMM yyyy");

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("")
    public String usuarioHome(HttpSession session, ModelMap model) {
        mostrarFecha(model);
        
        model.addAttribute("noticias", noticiaServicio.listarNoticias());
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            model.addAttribute("logueado", logueado.getNombre());
            System.out.println(logueado.getNombre());
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "mostrar_user";
    }

    private void mostrarFecha(ModelMap model) {
        String date = dateFormat.format(new Date());
        model.addAttribute("fecha", date);
    }
}
