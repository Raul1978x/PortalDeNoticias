package com.egg.noticias.entidades;

import java.util.ArrayList;

/**
 *
 * @author Raúl Gómez
 */

@Entity
@Data
public class Periodista extends Usuario{
    
    protected ArrayList<Noticia> misNoticias;
    protected Integer sueldoMensual;

}
