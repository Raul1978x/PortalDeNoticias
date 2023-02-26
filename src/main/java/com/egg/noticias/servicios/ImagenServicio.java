package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Imagen;
import com.egg.noticias.excepciones.MiExcepcion;
import com.egg.noticias.repositorios.IImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Raúl Gómez
 */
@Service
public class ImagenServicio {

    @Autowired
    private IImagenRepositorio imagenRepositorio;
/**
 * Método para guardar la Imagen de perfil
 * @param archivo
 * @return
 * @throws MiExcepcion 
 */
    public Imagen guardar(MultipartFile archivo) throws MiExcepcion {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());

                imagen.setNombre(archivo.getName());

                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
/**
 * Metodo para actualizar imagen de perfil
 * @param archivo
 * @param idImagen
 * @return
 * @throws MiExcepcion 
 */
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiExcepcion {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());

                imagen.setNombre(archivo.getName());

                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
