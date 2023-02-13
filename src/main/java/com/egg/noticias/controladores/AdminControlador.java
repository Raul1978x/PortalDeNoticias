package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.excepciones.MiExcepcion;
import com.egg.noticias.servicios.NoticiaServicio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;
    DateFormat dateFormat = new SimpleDateFormat("EEEEEEEEEE, d MMM yyyy");
    
    @GetMapping("")
    public String homeAdmin(ModelMap model) {
        mostrarFecha(model);
        model.addAttribute("noticias", noticiaServicio.listarNoticias());
        return "mostrar_admin";
    }
    
    @GetMapping("/cargar")
    public String noticia(ModelMap model) {
        mostrarFecha(model);
        return "noticiaForm";
    }

    @PostMapping("/carga")
    public String cargarNoticia(@RequestParam String titulo,
            @RequestParam String cuerpo, @RequestParam String bajada,
            @RequestParam(required = false) String imagen, ModelMap model)
            throws MiExcepcion {
        mostrarFecha(model);
        try {
            List<Noticia> noticias = noticiaServicio.listarNoticias();
            model.put("noticias", noticias);
            noticiaServicio.crearNoticia(titulo, cuerpo, imagen, bajada);
            model.put("exito", "La Noticia fue cargada exitosamente");

        } catch (MiExcepcion e) {
            model.put("error", e.getMessage());

            return "noticiaForm";
        }
        return "mostrar_admin";

    }
    
//    @GetMapping("/editar")
//    public String editar(){
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap model){
        Noticia noticia = noticiaServicio.buscarNoticiaPorId(id);
        model.put("noticia", noticia);
        return "noticiaEditar";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarPorId(@PathVariable String id, ModelMap model){
        noticiaServicio.eliminarPorId(id);
        return "mostrar_admin";
    }
//    @GetMapping("/modificar/{isbn}")
//    public String modificar(@PathVariable Long isbn, ModelMap modelo) {
//        modelo.addAttribute("autores", autores);
//        modelo.addAttribute("editoriales", editoriales);
//        
//        return "libro_modificar.html";
//    }
//    @PostMapping("/edita")
//    public String edita(ModelMap model){
//        
//        return "noticiaEditar";
//    }
    
     private void mostrarFecha(ModelMap model){
        String date = dateFormat.format(new Date());
        model.addAttribute("fecha", date);
    }
}