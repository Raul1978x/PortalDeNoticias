package com.egg.noticias.entidades;

import com.egg.noticias.emuneradores.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    protected String nombre;
    protected String email;
    protected String password;
    
    @Enumerated(EnumType.STRING)
    protected Rol rol;
    
    @OneToOne
    protected Imagen imagen;
}
