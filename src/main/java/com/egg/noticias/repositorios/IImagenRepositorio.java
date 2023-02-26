package com.egg.noticias.repositorios;

import com.egg.noticias.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Raúl Gómez
 */
@Repository
public interface IImagenRepositorio  extends JpaRepository<Imagen, String>{

}
