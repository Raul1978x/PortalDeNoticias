package com.egg.noticias.repositorios;

import com.egg.noticias.entidades.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Raúl Gómez
 */

@Repository
public interface INoticiaRepositorio extends JpaRepository<Noticia, String> {

//    @Query("SELECT n FROM Noticia n WHERE n.cuerpo, n.bajada, n.titulo LIKE: busqueda")
//    public void buscarPalabra(@Param("busqueda") String busqueda);
}
