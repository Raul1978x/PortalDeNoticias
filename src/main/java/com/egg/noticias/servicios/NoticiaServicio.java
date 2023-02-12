package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.excepciones.MiExcepcion;
import com.egg.noticias.repositorios.INoticiaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Raúl Gómez
 */
@Service
public class NoticiaServicio {

    /**
     *
     */
    @Autowired
    public INoticiaRepositorio noticiaRepositorio;

    /**
     * Metodo para persistir datos en el repositorio desde el servicio
     *
     * @param titulo
     * @param cuerpo
     * @param imagen
     * @throws com.egg.noticias.excepciones.MiExcepcion
     */
    @Transactional
    public void crearNoticia(String titulo, String cuerpo, String imagen, String bajada) throws MiExcepcion {

        validar(titulo, cuerpo, imagen,bajada);

        Noticia noticia = new Noticia();

        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setImagen(imagen);
        noticia.setBajada(bajada);

        noticiaRepositorio.save(noticia);
    }

    /**
     * Metodo para actualizar datos en el repositorio desde el servicio
     *
     * @param id
     * @param titulo
     * @param cuerpo
     * @param imagen
     * @param bajada
     */
    @Transactional
    public void actualizar(String id, String titulo, String cuerpo, String imagen, String bajada) {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticia.setImagen(imagen);
            noticia.setBajada(bajada);
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
     * @param idNoticia
     */
    
    @Transactional
    public void eliminar(String idNoticia) {
        noticiaRepositorio.deleteById(idNoticia);
    }
    

    private void validar(String titulo, String cuerpo, String imagen, String bajada) throws MiExcepcion {
        if (titulo == null || titulo.isEmpty()) {
            throw new MiExcepcion("el título no puede ser nulo ni estar vacío");
        }
        if (cuerpo == null || cuerpo.isEmpty()) {
            throw new MiExcepcion("el cuerpo de la noticia no puede ser nulo ni estar vacío");
        }
        if (bajada == null || bajada.isEmpty()) {
            throw new MiExcepcion("la bajada de la noticia no puede ser nulo ni estar vacía");
        }
        if (imagen == null || imagen.isEmpty()) {
            throw new MiExcepcion("la imágen no puede ser nulo ni estar vacía");
        }
    }
}
