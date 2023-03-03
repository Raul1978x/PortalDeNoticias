package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Imagen;
import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.excepciones.MiExcepcion;
import com.egg.noticias.repositorios.INoticiaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Raúl Gómez
 */
@Service
public class NoticiaServicio {

    @Autowired
    private INoticiaRepositorio noticiaRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    /**
     * Metodo para persistir datos en el repositorio desde el servicio
     *
     * @param archivo
     * @param titulo
     * @param cuerpo
     * @param bajada
     * @throws com.egg.noticias.excepciones.MiExcepcion
     */
    @Transactional
    public void crearNoticia(MultipartFile archivo, String titulo, String cuerpo, String bajada) throws MiExcepcion {

        validar(titulo, cuerpo, bajada);

        Noticia noticia = new Noticia();

        noticia.setTitulo(titulo.replace(System.lineSeparator(), "<br>"));
        noticia.setCuerpo(cuerpo.replace(System.lineSeparator(), "<br>"));
        noticia.setBajada(bajada.replace(System.lineSeparator(), "<br>"));

        Imagen imagen = imagenServicio.guardar(archivo);

        noticia.setImagen(imagen);

        noticiaRepositorio.save(noticia);
    }

    /**
     * Metodo para actualizar datos en el repositorio desde el servicio
     *
     * @param archivo
     * @param id
     * @param titulo
     * @param cuerpo
     * @param bajada
     */
    @Transactional
    public void actualizar(MultipartFile archivo, String id, String titulo, String cuerpo, String bajada) throws MiExcepcion {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo.replace(System.lineSeparator(), "<br>"));
            noticia.setCuerpo(cuerpo.replace(System.lineSeparator(), "<br>"));
//            noticia.setCuerpo(cuerpo);
            Imagen imagen = imagenServicio.guardar(archivo);
            noticia.setImagen(imagen);
//            noticia.setBajada(bajada);
            noticia.setBajada(bajada.replace(System.lineSeparator(), "<br>"));
            noticiaRepositorio.save(noticia);
        }
    }

    /**
     * Metodo para listar Noticias desde el repositorio
     *
     * @return List<Noticias>
     */
    @Transactional(readOnly = true)
    public List<Noticia> listarNoticias() {

        List<Noticia> noticias = new ArrayList();

        noticias = noticiaRepositorio.findAll();

        return noticias;
    }

    /**
     * Metodo para eliminar noticia por id
     *
     * @param id
     */
    @Transactional
    public void eliminarPorId(String id) {
        noticiaRepositorio.deleteById(id);
    }

    /**
     * Metodo para buscar noticia por id
     *
     * @param id
     * @return noticia
     */
    @Transactional
    public Noticia buscarNoticiaPorId(String id) {
        Noticia noticia = noticiaRepositorio.buscarNoticiaPorId(id);
        return noticia;
    }

//      private String convertirAHtml(String texto) {
    // utilizar una librería como Jsoup o HTMLCleaner para convertir texto en HTML
//            texto.replace(System.lineSeparator(), "<br>");
//        String html = Jsoup.parse(texto).body().html();
//        return html;
//    }
//    private String convertirAHtml(String texto) {
//        // utilizar HTMLCleaner para convertir texto en HTML
//        texto.replace(System.lineSeparator(), "<br>");
//        HtmlCleaner cleaner = new HtmlCleaner();
//        TagNode root = cleaner.clean(texto);
//        return cleaner.getInnerHtml(root);
//    }
    private void validar(String titulo, String cuerpo, String bajada) throws MiExcepcion {
        if (titulo == null || titulo.isEmpty()) {
            throw new MiExcepcion("el título no puede ser nulo ni estar vacío");
        }
        if (cuerpo == null || cuerpo.isEmpty()) {
            throw new MiExcepcion("el cuerpo de la noticia no puede ser nulo ni estar vacío");
        }
        if (bajada == null || bajada.isEmpty()) {
            throw new MiExcepcion("la bajada de la noticia no puede ser nulo ni estar vacía");
        }
//        if (archivo == null) {
//            throw new MiExcepcion("la imágen no puede ser nulo ni estar vacía");
//        }
    }
}
