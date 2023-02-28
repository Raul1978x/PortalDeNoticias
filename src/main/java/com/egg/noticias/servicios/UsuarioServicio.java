package com.egg.noticias.servicios;

import com.egg.noticias.emuneradores.Rol;
import com.egg.noticias.entidades.Imagen;
import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.excepciones.MiExcepcion;
import com.egg.noticias.repositorios.IUsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    

    @Transactional
    public void registrar(MultipartFile archivo,String nombre, String email, String password, 
            String password2) throws MiExcepcion {
        
        validar(nombre, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);

        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setRol(Rol.USER);
        
        Imagen imagen = imagenServicio.guardar(archivo);
        
        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws MiExcepcion {

        validar(nombre, email, password, password2);
        System.out.println(idUsuario + " pedido de nachoooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);

            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuario.setRol(Rol.USER);
            
//            String idImagen = null;
//            
//            if (usuario.getImagen() != null) {
//                idImagen = usuario.getImagen().getId();
//            }
            
            Imagen imagen = imagenServicio.guardar(archivo);
            
            usuario.setImagen(imagen);
            
            usuarioRepositorio.save(usuario);
            
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
           return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        }
        return null;
    }
    
    public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }

    public List<Usuario> usuarios(){
        return usuarioRepositorio.findAll();
    }
    private void validar(String nombre, String email, String password, 
            String password2) throws MiExcepcion {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("el nombre del usuario no puede ser nulo ni estar vacío");
        }
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("el email no puede ser nulo ni estar vacío");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiExcepcion("el password del usuario no puede estar vacío y debe tener más de 5 digitos");
        }
        if (!password2.equals(password)) {
            throw new MiExcepcion("los passwords ingresados deben ser iguales");
        }
    }
}
