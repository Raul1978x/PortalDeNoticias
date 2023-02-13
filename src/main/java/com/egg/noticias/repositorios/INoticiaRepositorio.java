package com.egg.noticias.repositorios;

import com.egg.noticias.entidades.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Raúl Gómez
 */

@Repository
public interface INoticiaRepositorio extends JpaRepository<Noticia, String> {

    @Query("SELECT n FROM Noticia n WHERE n.id= :id")
    public Noticia buscarNoticiaPorId(@Param("id") String id);
}
