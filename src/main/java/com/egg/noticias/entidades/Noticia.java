package com.egg.noticias.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Raúl Gómez
 */
@Data
@Entity
public class Noticia implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;
    
    private String titulo;
    private String bajada;
    private String cuerpo;
    private String imagen;
    
    @ManyToOne
    private Periodista creador;
}
